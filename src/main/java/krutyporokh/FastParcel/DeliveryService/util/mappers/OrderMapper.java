package krutyporokh.FastParcel.DeliveryService.util.mappers;

import krutyporokh.FastParcel.DeliveryService.DTO.OrderResponseDTO;
import krutyporokh.FastParcel.DeliveryService.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDTO toDto(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setClientId(order.getClient().getClientId());
        dto.setOfficeId(order.getSourceOffice().getOfficeId());
        dto.setWeight(order.getWeight());
        dto.setOrderCategory(order.getOrderCategory().getOrderCategoryId());
        dto.setDestinationOfficeId(order.getDestinationOffice().getOfficeId());

        return dto;
    }
}