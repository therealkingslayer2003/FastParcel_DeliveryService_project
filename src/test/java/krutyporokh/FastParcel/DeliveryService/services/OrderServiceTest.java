package krutyporokh.FastParcel.DeliveryService.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import krutyporokh.FastParcel.DeliveryService.DTO.OrderCreateDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.OrderResponseDTO;
import krutyporokh.FastParcel.DeliveryService.models.*;
import krutyporokh.FastParcel.DeliveryService.repositories.order.OrderRepository;
import krutyporokh.FastParcel.DeliveryService.util.mappers.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private OfficeService officeService;

    @Mock
    private OrderCategoryService orderCategoryService;

    @Mock
    private OrderStatusService orderStatusService;

    @Mock
    private OrderStatusHistoryService orderStatusHistoryService;

    @Mock
    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateNewOrder() {
        OrderCreateDTO createDTO = new OrderCreateDTO();

        createDTO.setClientId(1);
        createDTO.setWeight(22.1);
        createDTO.setOfficeId(2);
        createDTO.setOrderCategoryId(1);
        createDTO.setDestinationOfficeId(1);


        when(clientService.findById(createDTO.getClientId())).thenReturn(Optional.of(new Client()));
        when(officeService.findById(createDTO.getOfficeId())).thenReturn(Optional.of(new Office()));
        when(orderCategoryService.findById(createDTO.getOrderCategoryId())).thenReturn(Optional.of(new OrderCategory()));
        when(orderStatusService.findByOrderStatusName("ACCEPTED")).thenReturn(Optional.of(new OrderStatus()));
        when(officeService.findById(createDTO.getDestinationOfficeId())).thenReturn(Optional.of(new Office()));

        Order orderMock = mock(Order.class);
        when(orderRepository.save(any())).thenReturn(orderMock);

        OrderResponseDTO responseMock = mock(OrderResponseDTO.class);
        when(orderMapper.toDto(any())).thenReturn(responseMock);

        OrderResponseDTO result = orderService.createNewOrder(createDTO);

        assertNotNull(result);
        assertEquals(responseMock, result);
        verify(orderRepository).save(any());
        verify(orderStatusHistoryService).createNewOrderStatusHistory(any(), any());
    }

    @Test
    void shouldChangeOrderStatusAndRecordHistory() {
        Integer orderId = 1;
        String status = "NEW_STATUS";

        Order order = new Order();
        OrderStatus orderStatus = new OrderStatus();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderStatusService.findByOrderStatusName(status)).thenReturn(Optional.of(orderStatus));

        orderService.changeOrderStatusAndRecordHistory(orderId, status);

        assertEquals(orderStatus, order.getOrderStatus());
        verify(orderRepository).save(order);
        verify(orderStatusHistoryService).createNewOrderStatusHistory(order, orderStatus);
    }

    @Test
    void shouldFindById() {
        Integer orderId = 1;
        Order order = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.findById(orderId);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }
}
