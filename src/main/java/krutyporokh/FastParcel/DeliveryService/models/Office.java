package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "office")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int officeId;
    @Column(name = "office_location")
    private String officeLocation;
    @OneToMany(mappedBy = "office")
    private List<Employee> employeeList;
    @OneToMany(mappedBy = "office")
    private List<Order> orderList;
}
