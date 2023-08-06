package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.models.OrderCategory;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderCategoryRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderCategoryService {
    private final OrderCategoryRepository orderCategoryRepository;
    public Optional<OrderCategory> findById(Integer orderCategory) {
        return orderCategoryRepository.findById(orderCategory);
    }
}
