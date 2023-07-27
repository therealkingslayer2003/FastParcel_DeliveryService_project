package krutyporokh.FastParcel.DeliveryService.repositories.shipment;

import krutyporokh.FastParcel.DeliveryService.models.ShipmentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, Integer> {
}
