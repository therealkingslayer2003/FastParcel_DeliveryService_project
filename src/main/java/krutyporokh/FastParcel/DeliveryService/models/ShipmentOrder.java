package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import krutyporokh.FastParcel.DeliveryService.util.ShipmentOrderId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "shipment_order")
@Data
@EqualsAndHashCode
public class ShipmentOrder {
    @EmbeddedId
    private ShipmentOrderId shipmentOrderId;

    @MapsId("shipmentId")
    @ManyToOne
    @JoinColumn(name="shipment_id")
    private Shipment shipment;

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

}
