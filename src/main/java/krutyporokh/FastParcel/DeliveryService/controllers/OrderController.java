package krutyporokh.FastParcel.DeliveryService.controllers;

import jakarta.validation.Valid;
import krutyporokh.FastParcel.DeliveryService.DTO.OrderCreateDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.OrderResponseDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.OrderStatusChangeDTO;
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
    public ResponseEntity<OrderResponseDTO> createNewOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO){
        OrderResponseDTO orderResponseDTO = orderService.createNewOrder(orderCreateDTO);
        return new ResponseEntity<>(orderResponseDTO,HttpStatus.CREATED);
    }

    @PostMapping("/order-status-change")
    public ResponseEntity<?> changeOrderStatus(@RequestBody OrderStatusChangeDTO request) {
        orderService.changeOrderStatusAndRecordHistory(request.getOrderId(), request.getOrderStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
