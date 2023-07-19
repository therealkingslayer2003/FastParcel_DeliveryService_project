package krutyporokh.FastParcel.DeliveryService.repositories.order;

import krutyporokh.FastParcel.DeliveryService.models.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Integer> {
}
