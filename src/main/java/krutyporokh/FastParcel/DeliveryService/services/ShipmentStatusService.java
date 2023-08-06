package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.models.Shipment;
import krutyporokh.FastParcel.DeliveryService.models.ShipmentStatus;
import krutyporokh.FastParcel.DeliveryService.models.ShipmentStatusHistory;
import krutyporokh.FastParcel.DeliveryService.repositories.shipment.ShipmentStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShipmentStatusService {
    private final ShipmentStatusRepository shipmentStatusRepository;
    private final EmployeeService employeeService;
    private final ShipmentStatusHistoryService shipmentStatusHistoryService;
    public void updateShipmentStatus(Shipment shipment, String status) {

        ShipmentStatus shipmentStatus = shipmentStatusRepository.findByShipmentStatusName(status)
                .orElseThrow(() -> new RuntimeException("Shipment status not found"));

        shipment.setShipmentStatus(shipmentStatus);

        // Creating the new ShipmentStatusHistory object
        ShipmentStatusHistory shipmentStatusHistory = new ShipmentStatusHistory();
        shipmentStatusHistory.setShipment(shipment);
        shipmentStatusHistory.setShipmentStatus(shipmentStatus);

        shipmentStatusHistory.setDriver(employeeService.findById(employeeService.getAuthenticatedEmployeeId())
                .orElseThrow(() -> new RuntimeException("Driver not found")));

        shipmentStatusHistory.setChangedAt(LocalDateTime.now());

        // Saving the ShipmentStatusHistory object
        shipmentStatusHistoryService.save(shipmentStatusHistory);
    }

    public Optional<ShipmentStatus> findByShipmentStatusName(String statusName) {
        return shipmentStatusRepository.findByShipmentStatusName(statusName);
    }
}
