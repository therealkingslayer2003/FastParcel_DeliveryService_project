package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "\"order\"")
@Data
@EqualsAndHashCode
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "source_office_id")
    private Office sourceOffice;
    @Column(name = "weight")
    private double weight;
    @ManyToOne
    @JoinColumn(name = "order_category_id")
    private OrderCategory orderCategory;
    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn(name = "destination_office_id")
    private Office destinationOffice;
    @ManyToMany(mappedBy = "orders")
    private List<Shipment> shipments;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderStatusHistory> statusHistory;
}
