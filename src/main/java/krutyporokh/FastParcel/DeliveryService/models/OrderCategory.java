package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_category")
@Data
public class OrderCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderCategoryId;
    @Column(name = "order_category_name")
    private String orderCategoryName;
}
