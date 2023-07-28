package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.OrderDTO;
import krutyporokh.FastParcel.DeliveryService.models.Order;
import krutyporokh.FastParcel.DeliveryService.models.OrderStatus;
import krutyporokh.FastParcel.DeliveryService.models.OrderStatusHistory;
import krutyporokh.FastParcel.DeliveryService.repositories.ClientRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.OfficeRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.employee.EmployeeRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderCategoryRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderStatusHistoryRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderStatusRepository;
import krutyporokh.FastParcel.DeliveryService.security.EmployeeDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OfficeRepository officeRepository;
    private final OrderCategoryRepository orderCategoryRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final EmployeeRepository employeeRepository;


    public OrderDTO createNewOrder(OrderDTO orderDTO) {
        Order orderToSave = new Order();    //Creating a new order

        //Filling the fields
        orderToSave.setClient(clientRepository.findById(orderDTO.getClientId()).
                orElseThrow(() -> new RuntimeException("Client not found")));
        orderToSave.setSourceOffice(officeRepository.findById(orderDTO.getOfficeId()).
                orElseThrow(() -> new RuntimeException("Office not found")));
        orderToSave.setWeight(orderDTO.getWeight());
        orderToSave.setOrderCategory(orderCategoryRepository.findById(orderDTO.getOrderCategory()).
                orElseThrow(() -> new RuntimeException("Order category not found")));
        orderToSave.setOrderStatus(orderStatusRepository.findByOrderStatusName("ACCEPTED").
                orElseThrow(() -> new RuntimeException("Order status not found")));

        orderRepository.save(orderToSave);

        createOrderStatusHistory(orderToSave);

        return convertToDTO(orderToSave);
    }

    private void createOrderStatusHistory(Order order){

        //Creating an order status history for the order
        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
        orderStatusHistory.setOrder(order);
        orderStatusHistory.setOrderStatus(order.getOrderStatus());

        orderStatusHistory.setEmployee(employeeRepository.findById(getAuthenticatedEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found")));

        orderStatusHistory.setChangedAt(LocalDateTime.now());

        orderStatusHistoryRepository.save(orderStatusHistory);
    }
    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setClientId(order.getClient().getClientId());
        dto.setOfficeId(order.getSourceOffice().getOfficeId());
        dto.setWeight(order.getWeight());
        dto.setOrderCategory(order.getOrderCategory().getOrderCategoryId());

        return dto;
    }


    public void updateOrderStatus(String status, Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderStatus orderStatus = orderStatusRepository.findByOrderStatusName(status)
                .orElseThrow(() -> new RuntimeException("Order status not found"));

        // Check if the current order status is not the same as the new status
        if (!order.getOrderStatus().getOrderStatusName().equals(status)) {
            order.setOrderStatus(orderStatus);

            // Creating the new OrderStatusHistory object
            OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
            orderStatusHistory.setOrder(order);
            orderStatusHistory.setOrderStatus(orderStatus);

            orderStatusHistory.setEmployee(employeeRepository.findById(getAuthenticatedEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found")));

            orderStatusHistory.setChangedAt(LocalDateTime.now());

            // Saving the OrderStatusHistory object
            orderStatusHistoryRepository.save(orderStatusHistory);
        } else {
            throw new RuntimeException("The current order status is the same as the new status");
        }
    }

    public void changeOrderStatusToWarehouse(Integer orderId) {
        updateOrderStatus("IN_THE_WAREHOUSE", orderId);
    }

    public void changeOrderStatusToShipment(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // Check if current status is IN_THE_WAREHOUSE before changing to READY_FOR_SHIPMENT
        if (order.getOrderStatus().getOrderStatusName().equals("IN_THE_WAREHOUSE")) {
            updateOrderStatus("READY_FOR_SHIPMENT", orderId);
        } else {
            throw new RuntimeException("Order status is not IN_THE_WAREHOUSE");
        }
    }

    public void changeOrderStatusToTransit(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // Check if current status is READY_FOR_SHIPMENT before changing to IN_TRANSIT
        if (order.getOrderStatus().getOrderStatusName().equals("READY_FOR_SHIPMENT")) {
            updateOrderStatus("IN_TRANSIT", orderId);
        } else {
            throw new RuntimeException("Order status is not READY_FOR_SHIPMENT");
        }
    }

    public void changeOrderStatusToDelivered(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // Check if current status is IN_TRANSIT before changing to DELIVERED
        if (order.getOrderStatus().getOrderStatusName().equals("IN_TRANSIT")) {
            updateOrderStatus("DELIVERED", orderId);
        } else {
            throw new RuntimeException("Current order status is not IN_TRANSIT");
        }
    }

    public void changeOrderStatusToReceived(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // Check if current status is DELIVERED before changing to RECEIVED
        if (order.getOrderStatus().getOrderStatusName().equals("DELIVERED")) {
            updateOrderStatus("RECEIVED", orderId);
        } else {
            throw new RuntimeException("Current order status is not DELIVERED");
        }
    }

    //Getting the user from current session
    public Integer getAuthenticatedEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
        return employeeDetails.getEmployeeId();
    }
}