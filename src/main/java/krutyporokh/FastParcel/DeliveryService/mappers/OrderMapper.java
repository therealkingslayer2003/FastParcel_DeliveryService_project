package krutyporokh.FastParcel.DeliveryService.mappers;

import org.springframework.stereotype.Component;
import krutyporokh.FastParcel.DeliveryService.models.Order;
import krutyporokh.FastParcel.DeliveryService.DTO.OrderDTO;

@Component
public class OrderMapper {

    public OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();

        dto.setClientId(order.getClient().getClientId());
        dto.setOfficeId(order.getSourceOffice().getOfficeId());
        dto.setWeight(order.getWeight());
        dto.setOrderCategory(order.getOrderCategory().getOrderCategoryId());

        return dto;
    }
}
