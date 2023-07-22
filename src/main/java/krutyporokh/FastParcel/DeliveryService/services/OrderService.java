package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.OrderDTO;
import krutyporokh.FastParcel.DeliveryService.models.Order;
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

        //Getting a user from his session
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();

        Integer employeeId = employeeDetails.getEmployeeId();

        orderStatusHistory.setEmployee(employeeRepository.findById(employeeId).
                orElseThrow(() -> new RuntimeException("Employee not found")));

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


}