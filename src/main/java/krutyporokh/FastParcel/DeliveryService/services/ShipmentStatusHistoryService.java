package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentCreateDTO;
import krutyporokh.FastParcel.DeliveryService.models.Shipment;
import krutyporokh.FastParcel.DeliveryService.models.ShipmentStatusHistory;
import krutyporokh.FastParcel.DeliveryService.repositories.employee.EmployeeRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.shipment.ShipmentStatusHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor
public class ShipmentStatusHistoryService {
    private final EmployeeRepository employeeRepository;
    private final ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;
    public void createShipmentStatusHistory(Shipment shipment, ShipmentCreateDTO shipmentCreateDTO) {
        ShipmentStatusHistory shipmentStatusHistory = new ShipmentStatusHistory();
        shipmentStatusHistory.setShipment(shipment);
        shipmentStatusHistory.setShipmentStatus(shipment.getShipmentStatus());

        shipmentStatusHistory.setDriver(employeeRepository.findById(shipmentCreateDTO.getDriverId()).
                orElseThrow(() -> new RuntimeException("Employee not found")));

        shipmentStatusHistory.setChangedAt(LocalDateTime.now());

        shipmentStatusHistoryRepository.save(shipmentStatusHistory);
    }

    public void save(ShipmentStatusHistory shipmentStatusHistory) {
        shipmentStatusHistoryRepository.save(shipmentStatusHistory);
    }
}
