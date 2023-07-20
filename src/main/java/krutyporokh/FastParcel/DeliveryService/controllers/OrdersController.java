package krutyporokh.FastParcel.DeliveryService.controllers;

import jakarta.validation.Valid;
import krutyporokh.FastParcel.DeliveryService.DTO.OrderDTO;
import krutyporokh.FastParcel.DeliveryService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch-worker")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_BRANCH_WORKER')")
public class OrdersController {
    private final OrderService orderService;

        @PostMapping("/create-new-order")
        public ResponseEntity<OrderDTO> createNewOrder(@Valid @RequestBody OrderDTO orderDTO){
            OrderDTO responseOrderDTO = orderService.createNewOrder(orderDTO);
            return new ResponseEntity<>(responseOrderDTO,HttpStatus.CREATED);
        }
}
