package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "shipment")
@Data
@EqualsAndHashCode
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
    @ManyToMany
    @JoinTable(
            name = "shipment_order",
            joinColumns = @JoinColumn(name = "shipment_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;
}