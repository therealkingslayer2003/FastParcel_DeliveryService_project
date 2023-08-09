package krutyporokh.FastParcel.DeliveryService.DTO;

import lombok.Data;

@Data
public class OrderStatusChangeDTO {
    private Integer orderId;
    private String orderStatus;
}
