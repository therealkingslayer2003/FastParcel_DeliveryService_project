package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.models.Order;
import krutyporokh.FastParcel.DeliveryService.models.OrderStatus;
import krutyporokh.FastParcel.DeliveryService.models.OrderStatusHistory;
import krutyporokh.FastParcel.DeliveryService.repositories.employee.EmployeeRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderStatusHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OrderStatusHistoryService {

    private final EmployeeService employeeService;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    //This method is used to record the history of an order
    public void createNewOrderStatusHistory(Order order, OrderStatus orderStatus) {
        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
        orderStatusHistory.setOrder(order);
        orderStatusHistory.setOrderStatus(orderStatus);

        orderStatusHistory.setEmployee(employeeService.findById(employeeService.getAuthenticatedEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found")));

        orderStatusHistory.setChangedAt(LocalDateTime.now());

        orderStatusHistoryRepository.save(orderStatusHistory);
    }
}
