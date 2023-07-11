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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int truckId;
    @OneToOne
    @MapsId
    private Driver driver;
    @Column(name = "model")
    private String model;
    @Column(name = "capacity")
    private double capacity;

}
