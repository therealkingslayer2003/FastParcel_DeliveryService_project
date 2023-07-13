package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "driver")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    private int employeeId;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @MapsId
    private Employee employee;
    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private Truck truck;
    @Column(name = "license_number")
    private String licenseNumber;
    @OneToMany(mappedBy = "driver")
    private List<Shipment> shipmentList;
}