package krutyporokh.FastParcel.DeliveryService.repositories.order;

import krutyporokh.FastParcel.DeliveryService.models.OrderCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCategoryRepository extends JpaRepository<OrderCategory, Integer> {
}
