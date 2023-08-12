package krutyporokh.FastParcel.DeliveryService.DTO;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Integer clientId;
    private Integer officeId;
    private double weight;
    private Integer orderCategory;
    private Integer destinationOfficeId;
}
