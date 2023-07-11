package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;
    @ManyToOne
    @JoinColumn(name = "employee_role_id")
    private EmployeeRole employeeRole;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name="password")
    private String password;
    @Column(name = "name")
    private String name;
    @OneToOne(mappedBy = "employee")
    @PrimaryKeyJoinColumn
    private Driver driver;
}
