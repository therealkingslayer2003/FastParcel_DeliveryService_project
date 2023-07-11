package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employee_role")
@Data
public class EmployeeRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeRoleId;
    @Column(name = "employee_role_name")
    private String employeeRoleName;
}