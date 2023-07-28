package krutyporokh.FastParcel.DeliveryService.repositories;

import krutyporokh.FastParcel.DeliveryService.models.Order;
import krutyporokh.FastParcel.DeliveryService.models.ShipmentOrder;
import krutyporokh.FastParcel.DeliveryService.util.ShipmentOrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentOrderRepository extends JpaRepository<ShipmentOrder, ShipmentOrderId> {
    List<ShipmentOrder> findAllByShipmentOrderId_ShipmentId(Integer shipmentId);
}
