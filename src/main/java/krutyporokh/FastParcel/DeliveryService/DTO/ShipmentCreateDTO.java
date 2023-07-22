package krutyporokh.FastParcel.DeliveryService.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class ShipmentCreateDTO {
    @NotNull(message = "Order id(-s) should be specified")
    @Size(min = 1, message = "Order list must contain at least 1 item")
    private List<Integer> orderIDs;
    @NotNull(message = "Driver id should not be empty")
    private Integer driverId;
}
