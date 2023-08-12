package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "order_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderStatusId;
    @Column(name = "order_status_name")
    private String orderStatusName;

    @OneToMany(mappedBy = "orderStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderStatusHistory> statusHistory;

    public OrderStatus(String orderStatusName){
        this.orderStatusName = orderStatusName;
    }
}
