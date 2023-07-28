package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.DriverShipmentsResponseDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentCreateDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentResponseDTO;
import krutyporokh.FastParcel.DeliveryService.models.*;
import krutyporokh.FastParcel.DeliveryService.repositories.ShipmentOrderRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.shipment.ShipmentRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.shipment.ShipmentStatusHistoryRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.shipment.ShipmentStatusRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.employee.EmployeeRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderRepository;
import krutyporokh.FastParcel.DeliveryService.security.EmployeeDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ShipmentStatusRepository shipmentStatusRepository;
    private final ShipmentOrderRepository shipmentOrderRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;

    public ShipmentResponseDTO createNewShipment(ShipmentCreateDTO shipmentCreateDTO) {
        // Getting all orders
        List<Order> orderList = getOrderList(shipmentCreateDTO.getOrderIDs());

        // Checking if orders have the "READY_FOR_SHIPMENT" status
        validateOrderListCheckStatus(orderList);

        // Checking if orders have one destination address
        validateOrderListCheckAddress(orderList);

        // Calculating total shipment weight
        double totalWeight = getTotalWeight(orderList);

        // Getting driver for shipment
        Driver driver = getDriver(shipmentCreateDTO.getDriverId());

        // Checking if truck capacity > total shipment weight
        checkTruckCapacity(driver.getTruck(), totalWeight);

        // Creating and saving the shipment
        Shipment shipment = createAndSaveShipment(driver, totalWeight, orderList);

        //Creating the shipment status history
        createShipmentStatusHistory(shipment, shipmentCreateDTO);
        
        // Converting the shipment to DTO and returning
        return convertToDTO(shipment);
    }

    private void  createShipmentStatusHistory(Shipment shipment, ShipmentCreateDTO shipmentCreateDTO) {
        ShipmentStatusHistory shipmentStatusHistory = new ShipmentStatusHistory();
        shipmentStatusHistory.setShipment(shipment);
        shipmentStatusHistory.setShipmentStatus(shipment.getShipmentStatus());

        shipmentStatusHistory.setDriver(employeeRepository.findById(shipmentCreateDTO.getDriverId()).
                orElseThrow(() -> new RuntimeException("Employee not found")));

        shipmentStatusHistory.setChangedAt(LocalDateTime.now());

        shipmentStatusHistoryRepository.save(shipmentStatusHistory);
    }

    private List<Order> getOrderList(List<Integer> orderIds) {
        return orderIds.stream()
                .map(orderId -> orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Shipment with id " + orderId + " not found")))
                .collect(Collectors.toList());
    }

    private void validateOrderListCheckStatus(List<Order> orderList){
        if(orderList.stream().anyMatch(order -> !order.getOrderStatus().
                getOrderStatusName().equals("READY_FOR_SHIPMENT"))){
            throw new RuntimeException("All orders must have the 'READY_FOR_SHIPMENT' status");
        }
    }
    private void validateOrderListCheckAddress(List<Order> orderList) {
        Office destinationOffice = orderList.get(0).getDestinationOffice();
        if (orderList.stream().anyMatch(order -> !order.getDestinationOffice().equals(destinationOffice))) {
            throw new RuntimeException("All orders must have the same destination office");
        }
    }

    private double getTotalWeight(List<Order> orderList) {
        return orderList.stream().mapToDouble(Order::getWeight).sum();
    }

    private Driver getDriver(Integer driverId) {
        return employeeRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found")).getDriver();
    }

    private void checkTruckCapacity(Truck truck, double totalWeight) {
        if (truck.getCapacity() < totalWeight) {
            throw new RuntimeException("Total weight of orders exceeds truck capacity");
        }
    }

    private Shipment createAndSaveShipment(Driver driver, double totalWeight, List<Order> orderList) {
        Shipment shipment = new Shipment();
        shipment.setDriver(driver);
        shipment.setShipmentStatus(shipmentStatusRepository.findByShipmentStatusName("FORMED").
                orElseThrow(() -> new RuntimeException("Shipment status 'FORMED' not found")));
        shipment.setTotalWeight(totalWeight);
        shipment.setSourceOffice(orderList.get(0).getSourceOffice());
        shipment.setDestinationOffice(orderList.get(0).getDestinationOffice());
        shipment.setOrders(orderList);
        return shipmentRepository.save(shipment);
    }

    private ShipmentResponseDTO convertToDTO(Shipment shipment) {
        ShipmentResponseDTO dto = new ShipmentResponseDTO();
        dto.setDriverForShipment(shipment.getDriver().getEmployee().getName());
        dto.setShipmentStatus(shipment.getShipmentStatus().getShipmentStatusName());
        dto.setTotalWeight(shipment.getTotalWeight());
        dto.setSourceOffice(shipment.getSourceOffice().getOfficeLocation());
        dto.setDestinationOffice(shipment.getDestinationOffice().getOfficeLocation());
        dto.setOrderList(shipment.getOrders().stream().map(Order::getOrderId).collect(Collectors.toList()));
        return dto;
    }

    public DriverShipmentsResponseDTO getAssignedShipments() {
        int driverId = getAuthenticatedEmployeeId();

        List<Shipment> shipments = shipmentRepository.findAllByDriver_EmployeeId(driverId);
        List<Integer> shipmentIds = shipments.stream().map(Shipment::getShipmentId).collect(Collectors.toList());
        DriverShipmentsResponseDTO response = new DriverShipmentsResponseDTO();
        response.setShipmentId(shipmentIds);

        return response;
    }

    public Integer getAuthenticatedEmployeeId() {
        //Getting a user from current session
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
        return employeeDetails.getEmployeeId();
    }

    private Shipment getShipment(Integer shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    private List<Order> getOrdersFromShipment(Integer shipmentId) {
        List<ShipmentOrder> shipmentOrders = shipmentOrderRepository.findAllByShipmentOrderId_ShipmentId(shipmentId);
        return shipmentOrders.stream().map(ShipmentOrder::getOrder).toList();
    }

    public void changeShipmentAndOrderStatusToTransit(Integer shipmentId) {
        Shipment shipment = getShipment(shipmentId);
        if (!shipment.getShipmentStatus().getShipmentStatusName().equals("FORMED")) {
            throw new RuntimeException("Current shipment status is not FORMED");
        }
        updateShipmentStatus("IN_TRANSIT", shipmentId);

        List<Order> orders = getOrdersFromShipment(shipmentId);
        orders.forEach(order -> orderService.changeOrderStatusToTransit(order.getOrderId()));
    }

    public void changeShipmentAndOrderStatusToDelivered(Integer shipmentId) {
        Shipment shipment = getShipment(shipmentId);
        if (!shipment.getShipmentStatus().getShipmentStatusName().equals("IN_TRANSIT")) {
            throw new RuntimeException("Current shipment status is not IN_TRANSIT");
        }
        updateShipmentStatus("DELIVERED", shipmentId);

        List<Order> orders = getOrdersFromShipment(shipmentId);
        orders.forEach(order -> orderService.changeOrderStatusToDelivered(order.getOrderId()));
    }

    public void updateShipmentStatus(String status, Integer shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        ShipmentStatus shipmentStatus = shipmentStatusRepository.findByShipmentStatusName(status)
                .orElseThrow(() -> new RuntimeException("Shipment status not found"));

        // Check if the current shipment status is not the same as the new status
        if (!shipment.getShipmentStatus().getShipmentStatusName().equals(status)) {
            shipment.setShipmentStatus(shipmentStatus);

            // Creating the new ShipmentStatusHistory object
            ShipmentStatusHistory shipmentStatusHistory = new ShipmentStatusHistory();
            shipmentStatusHistory.setShipment(shipment);
            shipmentStatusHistory.setShipmentStatus(shipmentStatus);

            shipmentStatusHistory.setDriver(employeeRepository.findById(getAuthenticatedEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Driver not found")));

            shipmentStatusHistory.setChangedAt(LocalDateTime.now());

            // Saving the ShipmentStatusHistory object
            shipmentStatusHistoryRepository.save(shipmentStatusHistory);
        } else {
            throw new RuntimeException("The current shipment status is the same as the new status");
        }
    }

    public ShipmentResponseDTO getTrackedShipment(int id) {
        Shipment shipment = shipmentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Shipment not found"));
        return convertToDTO(shipment);
    }
}
