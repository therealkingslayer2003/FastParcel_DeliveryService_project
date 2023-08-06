package krutyporokh.FastParcel.DeliveryService.mappers;

import org.springframework.stereotype.Component;
import krutyporokh.FastParcel.DeliveryService.models.Shipment;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentResponseDTO;
import krutyporokh.FastParcel.DeliveryService.models.Order;

import java.util.stream.Collectors;

@Component
public class ShipmentMapper {

    public ShipmentResponseDTO toDto(Shipment shipment) {
        ShipmentResponseDTO dto = new ShipmentResponseDTO();

        dto.setDriverForShipment(shipment.getDriver().getEmployee().getName());
        dto.setShipmentStatus(shipment.getShipmentStatus().getShipmentStatusName());
        dto.setTotalWeight(shipment.getTotalWeight());
        dto.setSourceOffice(shipment.getSourceOffice().getOfficeLocation());
        dto.setDestinationOffice(shipment.getDestinationOffice().getOfficeLocation());
        dto.setOrderList(shipment.getOrders().stream().map(Order::getOrderId).collect(Collectors.toList()));

        return dto;
    }
}
