package krutyporokh.FastParcel.DeliveryService.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShipmentStatusChangeDTO {
    @NotNull(message = "Shipment id should not be null")
    private Integer shipmentId;
    @NotNull(message = "Status name should not be null")
    private String statusName;
}
