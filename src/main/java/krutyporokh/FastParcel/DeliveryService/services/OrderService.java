package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.OrderCreateDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.OrderResponseDTO;
import krutyporokh.FastParcel.DeliveryService.util.mappers.OrderMapper;
import krutyporokh.FastParcel.DeliveryService.models.Order;
import krutyporokh.FastParcel.DeliveryService.models.OrderStatus;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final OfficeService officeService;
    private final OrderCategoryService orderCategoryService;
    private final OrderStatusService orderStatusService;
    private final OrderStatusHistoryService orderStatusHistoryService;
    private final OrderMapper orderMapper;


    public OrderResponseDTO createNewOrder(OrderCreateDTO orderCreateDTO) {
        Order orderToSave = new Order();    //Creating a new order

        //Filling the fields
        orderToSave.setClient(clientService.findById(orderCreateDTO.getClientId()).
                orElseThrow(() -> new RuntimeException("Client not found")));
        orderToSave.setSourceOffice(officeService.findById(orderCreateDTO.getOfficeId()).
                orElseThrow(() -> new RuntimeException("Office not found")));
        orderToSave.setWeight(orderCreateDTO.getWeight());
        orderToSave.setOrderCategory(orderCategoryService.findById(orderCreateDTO.getOrderCategoryId()).
                orElseThrow(() -> new RuntimeException("Order category not found")));
        orderToSave.setOrderStatus(orderStatusService.findByOrderStatusName("ACCEPTED").
                orElseThrow(() -> new RuntimeException("Order status not found")));
        orderToSave.setDestinationOffice(officeService.findById(orderCreateDTO.getDestinationOfficeId()).
                orElseThrow(() -> new RuntimeException("Destination office not found")));

        orderRepository.save(orderToSave);

        orderStatusHistoryService.createNewOrderStatusHistory(orderToSave, orderToSave.getOrderStatus());

        return orderMapper.toDto(orderToSave);
    }

    public void changeOrderStatusAndRecordHistory(Integer orderId, String newOrderStatus) {
        // Update the order status
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new RuntimeException("Order not found"));

        OrderStatus orderStatus = orderStatusService.findByOrderStatusName(newOrderStatus).
                orElseThrow(() -> new RuntimeException("Order status not found"));
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        // Record the status history
        orderStatusHistoryService.createNewOrderStatusHistory(order, orderStatus);
    }

    public Optional<Order> findById(Integer orderId) {
        return orderRepository.findById(orderId);
    }
}