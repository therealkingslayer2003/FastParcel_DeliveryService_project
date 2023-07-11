package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipment")
@Data
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shipmentId;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Driver driver;
    @ManyToOne
    @JoinColumn(name = "shipment_status_id")
    private ShipmentStatus shipmentStatus;
    @Column(name = "total_weight")
    private double totalWeight;
    @ManyToOne
    @JoinColumn(name = "current_office_id")
    private Office currentOffice;
    @ManyToOne
    @JoinColumn(name = "destination_office_id")
    private Office destinationOffice;
}