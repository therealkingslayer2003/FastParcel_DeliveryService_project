package krutyporokh.FastParcel.DeliveryService.repositories.shipment;

import krutyporokh.FastParcel.DeliveryService.models.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentStatusRepository extends JpaRepository<ShipmentStatus, Integer> {
    Optional<ShipmentStatus> findByShipmentStatusName(String shipmentStatusName);
}
