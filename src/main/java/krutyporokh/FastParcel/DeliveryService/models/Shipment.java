package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @JoinColumn(name = "source_office_id")
    private Office sourceOffice;
    @ManyToOne
    @JoinColumn(name = "destination_office_id")
    private Office destinationOffice;
    @OneToMany(mappedBy = "shipment")
    private List<ShipmentStatusHistory> shipmentStatusHistories;

    @ManyToMany
    @JoinTable(
            name = "shipment_order",
            joinColumns = @JoinColumn(name = "shipment_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;
}