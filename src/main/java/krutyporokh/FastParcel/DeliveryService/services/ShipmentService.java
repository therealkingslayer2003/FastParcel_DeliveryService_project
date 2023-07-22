package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentCreateDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentResponseDTO;
import krutyporokh.FastParcel.DeliveryService.models.*;
import krutyporokh.FastParcel.DeliveryService.repositories.DriverRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.ShipmentRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.ShipmentStatusRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.employee.EmployeeRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderRepository;
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
    private final ShipmentStatusRepository shipmentStatusRepository;
    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;

    public ShipmentResponseDTO createNewShipment(ShipmentCreateDTO shipmentCreateDTO) {
        // Getting all orders
        List<Order> orderList = getOrderList(shipmentCreateDTO.getOrderIDs());

        // Checking if orders have one destination address
        validateOrderList(orderList);

        // Calculating total shipment weight
        double totalWeight = getTotalWeight(orderList);

        // Getting driver for shipment
        Driver driver = getDriver(shipmentCreateDTO.getDriverId());

        // Checking if truck capacity > total shipment weight
        checkTruckCapacity(driver.getTruck(), totalWeight);

        // Creating and saving the shipment
        Shipment shipment = createAndSaveShipment(driver, totalWeight, orderList);

        // Converting the shipment to DTO and returning
        return convertToDTO(shipment);
    }

    private List<Order> getOrderList(List<Integer> orderIds) {
        return orderIds.stream()
                .map(orderId -> orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order with id " + orderId + " not found")))
                .collect(Collectors.toList());
    }

    private void validateOrderList(List<Order> orderList) {
        if(orderList.size() > 1){
            Office destinationOffice = orderList.get(0).getDestinationOffice();
            if (orderList.stream().anyMatch(order -> !order.getDestinationOffice().equals(destinationOffice))) {
                throw new RuntimeException("All orders must have the same destination office");
            }
        }
    }

    private double getTotalWeight(List<Order> orderList) {
        return orderList.stream().mapToDouble(Order::getWeight).sum();
    }

    private Driver getDriver(Integer driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
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
        shipment.setCurrentOffice(driver.getEmployee().getOffice());
        shipment.setDestinationOffice(orderList.get(0).getDestinationOffice());
        shipment.setOrders(orderList);
        return shipmentRepository.save(shipment);
    }

    private ShipmentResponseDTO convertToDTO(Shipment shipment) {
        ShipmentResponseDTO dto = new ShipmentResponseDTO();
        dto.setEmployee(shipment.getDriver().getEmployee().getName());
        dto.setShipmentStatus(shipment.getShipmentStatus().getShipmentStatusName());
        dto.setTotalWeight(shipment.getTotalWeight());
        dto.setCurrentOffice(shipment.getCurrentOffice().getOfficeLocation());
        dto.setDestinationOffice(shipment.getDestinationOffice().getOfficeLocation());
        dto.setOrderList(shipment.getOrders().stream().map(Order::getOrderId).collect(Collectors.toList()));
        return dto;
    }
}
