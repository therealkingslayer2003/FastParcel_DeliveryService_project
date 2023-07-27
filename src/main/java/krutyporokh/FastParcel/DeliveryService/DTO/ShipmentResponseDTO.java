package krutyporokh.FastParcel.DeliveryService.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ShipmentResponseDTO {
    private String driverForShipment;
    private String shipmentStatus;
    private Double totalWeight;
    private String sourceOffice;
    private String destinationOffice;
    private List<Integer> orderList;
}
