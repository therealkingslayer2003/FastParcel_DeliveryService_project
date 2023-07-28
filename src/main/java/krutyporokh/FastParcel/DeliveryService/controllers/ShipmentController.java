package krutyporokh.FastParcel.DeliveryService.controllers;

import krutyporokh.FastParcel.DeliveryService.DTO.DriverShipmentsResponseDTO;
import krutyporokh.FastParcel.DeliveryService.services.OrderService;
import krutyporokh.FastParcel.DeliveryService.services.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/shipment-and-orders-to-transit/{shipment-id}")
    public ResponseEntity<?> changeOrderAndShipmentStatusToTransit(@PathVariable("shipment-id") int shipmentId) {
        shipmentService.changeShipmentAndOrderStatusToTransit(shipmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/shipment-and-orders-to-delivered/{shipment-id}")
    public ResponseEntity<?> changeOrderAndShipmentStatusToDelivered(@PathVariable("shipment-id") int shipmentId) {
        shipmentService.changeShipmentAndOrderStatusToDelivered(shipmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
