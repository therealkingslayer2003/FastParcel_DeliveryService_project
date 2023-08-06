package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.models.OrderStatus;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public Optional<OrderStatus> findByOrderStatusName(String newOrderStatus) {
        return orderStatusRepository.findByOrderStatusName(newOrderStatus);
    }
}
