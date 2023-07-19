package krutyporokh.FastParcel.DeliveryService.repositories.order;

import krutyporokh.FastParcel.DeliveryService.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
