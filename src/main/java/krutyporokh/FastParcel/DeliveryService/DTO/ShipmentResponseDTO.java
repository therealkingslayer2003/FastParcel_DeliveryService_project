package krutyporokh.FastParcel.DeliveryService.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ShipmentResponseDTO {
    private String employee;
    private String shipmentStatus;
    private Double totalWeight;
    private String currentOffice;
    private String destinationOffice;
    private List<Integer> orderList;
}
