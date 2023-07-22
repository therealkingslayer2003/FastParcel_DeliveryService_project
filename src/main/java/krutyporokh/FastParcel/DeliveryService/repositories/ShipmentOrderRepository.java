package krutyporokh.FastParcel.DeliveryService.repositories;

import krutyporokh.FastParcel.DeliveryService.models.ShipmentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentOrderRepository extends JpaRepository<ShipmentOrder, Integer> {
}
