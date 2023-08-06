package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.models.ShipmentOrder;
import krutyporokh.FastParcel.DeliveryService.repositories.ShipmentOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShipmentOrderService {
    private final ShipmentOrderRepository shipmentOrderRepository;

    public List<ShipmentOrder> findAllByShipmentOrderId_ShipmentId(Integer shipmentId) {
        return shipmentOrderRepository.findAllByShipmentOrderId_ShipmentId(shipmentId);
    }
}
