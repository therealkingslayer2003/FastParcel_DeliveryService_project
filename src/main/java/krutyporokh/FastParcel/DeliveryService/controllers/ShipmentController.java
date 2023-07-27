package krutyporokh.FastParcel.DeliveryService.controllers;

import krutyporokh.FastParcel.DeliveryService.DTO.DriverShipmentsResponseDTO;
import krutyporokh.FastParcel.DeliveryService.services.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ROLE_DRIVER')") //For 1. Checking the assigned shipments
@RequestMapping("/driver")           // 2. Changing the shipment status
@AllArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;

    @GetMapping("/shipments")
    public ResponseEntity<?> getAssignedShipments(){
        DriverShipmentsResponseDTO response = shipmentService.getAssignedShipments();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
