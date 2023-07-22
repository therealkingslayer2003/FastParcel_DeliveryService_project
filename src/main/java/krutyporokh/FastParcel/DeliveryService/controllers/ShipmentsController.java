package krutyporokh.FastParcel.DeliveryService.controllers;

import jakarta.validation.Valid;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentCreateDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentResponseDTO;
import krutyporokh.FastParcel.DeliveryService.models.Shipment;
import krutyporokh.FastParcel.DeliveryService.services.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-manager")
@PreAuthorize("hasRole('ROLE_DELIVERY_MANAGER')")
@AllArgsConstructor
public class ShipmentsController {
    private final ShipmentService shipmentService;
    @PostMapping("/create-new-shipment")
    public ResponseEntity<ShipmentResponseDTO> createNewShipment(@Valid @RequestBody ShipmentCreateDTO shipmentCreateDTO){
        ShipmentResponseDTO shipmentResponseDTO = shipmentService.createNewShipment(shipmentCreateDTO);
        return new ResponseEntity<>(shipmentResponseDTO, HttpStatus.CREATED);
    }
}
