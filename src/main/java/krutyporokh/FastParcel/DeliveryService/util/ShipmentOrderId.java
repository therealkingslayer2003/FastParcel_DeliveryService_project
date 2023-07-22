package krutyporokh.FastParcel.DeliveryService.util;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class ShipmentOrderId implements Serializable {
    private int shipmentId;
    private int orderId;
}
