package krutyporokh.FastParcel.DeliveryService.util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class ShipmentOrderId implements Serializable {
    @Column(name = "shipment_id")
    private Integer shipmentId;
    @Column(name = "order_id")
    private Integer orderId;
}
