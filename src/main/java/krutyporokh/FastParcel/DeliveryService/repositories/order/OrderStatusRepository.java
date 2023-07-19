package krutyporokh.FastParcel.DeliveryService.repositories.order;

import krutyporokh.FastParcel.DeliveryService.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    Optional<OrderStatus> findByOrderStatusName(String orderStatusName);
}
