package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "truck")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Truck {
    @Id
    private int truckId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    private Driver driver;

    @Column(name = "model")
    private String model;

    @Column(name = "capacity")
    private double capacity;
}
