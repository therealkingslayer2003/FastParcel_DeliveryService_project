package krutyporokh.FastParcel.DeliveryService.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderDTO {
    @NotNull(message = "Client id should not be empty")
    private Integer clientId;
    @NotNull(message = "Office id should not be empty")
    private Integer officeId;
    @NotNull(message = "Weight should not be null")
    @Min(value = 1, message = "Weight should be min 1 kg")
    @Max(value = 2000, message = "Weight should be max 2000 kg")
    private double weight;
    @NotNull(message = "Order category should not be empty")
    private Integer orderCategory;
    @NotNull(message = "Destination office id should not be empty")
    private Integer destinationOfficeId;
}
