package krutyporokh.FastParcel.DeliveryService.DTO;


import krutyporokh.FastParcel.DeliveryService.models.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class EmployeeDto {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String employeeRole;
    private int officeId;
}
