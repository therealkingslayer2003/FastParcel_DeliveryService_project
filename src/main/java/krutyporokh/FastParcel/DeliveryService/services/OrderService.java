package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.OrderDTO;
import krutyporokh.FastParcel.DeliveryService.models.Order;
import krutyporokh.FastParcel.DeliveryService.models.OrderStatus;
import krutyporokh.FastParcel.DeliveryService.repositories.ClientRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.OfficeRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderCategoryRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderStatusRepository;
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


    public OrderDTO createNewOrder(OrderDTO orderDTO) {
        Order orderToSave = new Order();    //Creating a new order

        //Filling the fields
        orderToSave.setClient(clientService.findById(orderDTO.getClientId()).
                orElseThrow(() -> new RuntimeException("Client not found")));
        orderToSave.setSourceOffice(officeService.findById(orderDTO.getOfficeId()).
                orElseThrow(() -> new RuntimeException("Office not found")));
        orderToSave.setWeight(orderDTO.getWeight());
        orderToSave.setOrderCategory(orderCategoryService.findById(orderDTO.getOrderCategory()).
                orElseThrow(() -> new RuntimeException("Order category not found")));
        orderToSave.setOrderStatus(orderStatusService.findByOrderStatusName("ACCEPTED").
                orElseThrow(() -> new RuntimeException("Order status not found")));

        orderRepository.save(orderToSave);

        orderStatusHistoryService.createNewOrderStatusHistory(orderToSave, orderToSave.getOrderStatus());

        return convertToDTO(orderToSave);
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

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setClientId(order.getClient().getClientId());
        dto.setOfficeId(order.getSourceOffice().getOfficeId());
        dto.setWeight(order.getWeight());
        dto.setOrderCategory(order.getOrderCategory().getOrderCategoryId());

        return dto;
    }

    public Optional<Order> findById(Integer orderId) {
        return orderRepository.findById(orderId);
    }
}