package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_status")
@Data
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderStatusId;
    @Column(name = "order_status_name")
    private String orderStatusName;
}
