package krutyporokh.FastParcel.DeliveryService.controllers;

import jakarta.validation.Valid;
import krutyporokh.FastParcel.DeliveryService.DTO.OrderDTO;
import krutyporokh.FastParcel.DeliveryService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branch-worker")           //For 1. Registration a new order
@AllArgsConstructor                            // 2. Changing the order status
@PreAuthorize("hasRole('ROLE_BRANCH_WORKER')")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create-new-order")
    public ResponseEntity<OrderDTO> createNewOrder(@Valid @RequestBody OrderDTO orderDTO){
        OrderDTO responseOrderDTO = orderService.createNewOrder(orderDTO);
        return new ResponseEntity<>(responseOrderDTO,HttpStatus.CREATED);
    }

    @PostMapping("/order-to-warehouse/{id}")    //Order status from "ACCEPTED" to "IN_THE_WAREHOUSE"
    public ResponseEntity<?> changeOrderStatusToWarehouse(@PathVariable("id") Integer orderId) {
            orderService.changeOrderStatusToWarehouse(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/order-to-shipment/{id}")     //Order status from "IN_THE_WAREHOUSE" to "READY_FOR_SHIPMENT"
    public ResponseEntity<?> changeOrderStatusToShipment(@PathVariable("id") Integer orderId) {
        orderService.changeOrderStatusToShipment(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/order-to-received/{id}")     //Order status from "DELIVERED" to "RECEIVED"
    public ResponseEntity<?> changeOrderStatusToReceived(@PathVariable("id") Integer orderId) {
        orderService.changeOrderStatusToReceived(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
