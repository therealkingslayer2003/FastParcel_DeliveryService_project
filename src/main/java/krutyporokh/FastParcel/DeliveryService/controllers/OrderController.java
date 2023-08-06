package krutyporokh.FastParcel.DeliveryService.controllers;

import jakarta.validation.Valid;
import krutyporokh.FastParcel.DeliveryService.DTO.OrderDTO;
import krutyporokh.FastParcel.DeliveryService.services.EmployeeService;
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

    @PostMapping("/change-order-status/{id}/{newStatus}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable("id") Integer orderId, @PathVariable("newStatus") String newStatus) {
        orderService.changeOrderStatusAndRecordHistory(orderId, newStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
