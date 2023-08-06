package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.DriverShipmentsResponseDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentCreateDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentResponseDTO;
import krutyporokh.FastParcel.DeliveryService.models.*;
import krutyporokh.FastParcel.DeliveryService.repositories.shipment.ShipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ShipmentStatusService shipmentStatusService;
    private final ShipmentOrderService shipmentOrderService;
    private final OrderService orderService;
    private final EmployeeService employeeService;
    private final ShipmentStatusHistoryService shipmentStatusHistoryService;


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
        shipmentStatusHistoryService.createShipmentStatusHistory(shipment, shipmentCreateDTO);

        // Converting the shipment to DTO and returning
        return convertToDTO(shipment);
    }

    private List<Order> getOrderList(List<Integer> orderIds) {
        return orderIds.stream()
                .map(orderId -> orderService.findById(orderId)
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
        return employeeService.findById(driverId)
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
        shipment.setShipmentStatus(shipmentStatusService.findByShipmentStatusName("FORMED").
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
        int driverId = employeeService.getAuthenticatedEmployeeId();

        List<Shipment> shipments = shipmentRepository.findAllByDriver_EmployeeId(driverId);
        List<Integer> shipmentIds = shipments.stream().map(Shipment::getShipmentId).collect(Collectors.toList());
        DriverShipmentsResponseDTO response = new DriverShipmentsResponseDTO();
        response.setShipmentId(shipmentIds);

        return response;
    }

    private Shipment getShipment(Integer shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    private List<Order> getOrdersFromShipment(Integer shipmentId) {
        List<ShipmentOrder> shipmentOrders = shipmentOrderService.findAllByShipmentOrderId_ShipmentId(shipmentId);
        return shipmentOrders.stream().map(ShipmentOrder::getOrder).toList();
    }

    public void changeShipmentAndOrderStatus(Integer shipmentId, String newStatus) {
        Shipment shipment = getShipment(shipmentId);
        if (shipment.getShipmentStatus().getShipmentStatusName().equals(newStatus)) {
            throw new RuntimeException("Current shipment status is already " + newStatus);
        }
        shipmentStatusService.updateShipmentStatus(shipment, newStatus);

        List<Order> orders = getOrdersFromShipment(shipmentId);
        orders.forEach(order -> orderService.changeOrderStatusAndRecordHistory(order.getOrderId(), newStatus));
    }

    public ShipmentResponseDTO getTrackedShipment(int id) {
        Shipment shipment = shipmentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Shipment not found"));
        return convertToDTO(shipment);
    }

}
