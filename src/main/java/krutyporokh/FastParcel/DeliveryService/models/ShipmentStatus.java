package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shipment_status")
@Data
public class ShipmentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shipmentStatusId;
    @Column(name = "shipment_status_name")
    private String shipmentStatusName;
}