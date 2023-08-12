package krutyporokh.FastParcel.DeliveryService.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DriverCreateDTO extends EmployeeCreateDTO {
    @NotEmpty
    @Pattern(regexp = "^\\d{8}[A-Z]{2}$", message = "License number should keep the format: 12345678XX")
    private String licenseNumber;
    @NotEmpty
    @Size(min = 5, max = 100, message = "Model should be min 5 , max 100 symbols")
    private String model;
    @NotNull
    @DecimalMin(value = "1000.0")
    @DecimalMax(value = "8000.0")
    private Double capacity;
}
