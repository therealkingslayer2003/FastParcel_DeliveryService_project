package krutyporokh.FastParcel.DeliveryService.DTO;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeDTO {
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 4, max = 100, message = "Password should be min = 4 symbols , max = 100 symbols")
    private String password;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be min = 2 symbols , max = 100 symbols")
    private String name;
    @NotEmpty(message = "Phone number should not be empty")
    @Pattern(regexp="^\\+353-\\d{2}-\\d{3}-\\d{4}$", message="Phone number should be in format +353-xx-xxx-xxxx")
    private String phoneNumber;
    @NotEmpty(message = "Employee role should not be empty")
    private String employeeRole;
    @NotNull(message = "Office id should not be empty")
    private Integer officeId;
}
