package krutyporokh.FastParcel.DeliveryService.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import krutyporokh.FastParcel.DeliveryService.DTO.DriverShipmentsResponseDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentCreateDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.ShipmentResponseDTO;
import krutyporokh.FastParcel.DeliveryService.models.*;
import krutyporokh.FastParcel.DeliveryService.repositories.shipment.ShipmentRepository;
import krutyporokh.FastParcel.DeliveryService.util.mappers.ShipmentMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ShipmentServiceTest {

    @InjectMocks
    private ShipmentService shipmentService;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ShipmentStatusService shipmentStatusService;

    @Mock
    private ShipmentStatusHistoryService shipmentStatusHistoryService;

    @Mock
    private ShipmentMapper shipmentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateNewShipment() {
        ShipmentCreateDTO createDTO = new ShipmentCreateDTO();
        createDTO.setDriverId(11);
        createDTO.setOrderIDs(Arrays.asList(1, 2, 3));

        OrderStatus readyForShipmentStatus = new OrderStatus("READY_FOR_SHIPMENT");
        Office sourceOffice = new Office();
        sourceOffice.setOfficeId(2);
        Office destinationOffice = new Office();
        destinationOffice.setOfficeId(3);

        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setOrderStatus(readyForShipmentStatus);
        order1.setWeight(10.0);
        order1.setSourceOffice(sourceOffice);
        order1.setDestinationOffice(destinationOffice);

        Order order2 = new Order();
        order2.setOrderId(2);
        order2.setOrderStatus(readyForShipmentStatus);
        order2.setWeight(15.0);
        order2.setSourceOffice(sourceOffice);
        order2.setDestinationOffice(destinationOffice);

        Order order3 = new Order();
        order3.setOrderId(3);
        order3.setOrderStatus(readyForShipmentStatus);
        order3.setWeight(20.0);
        order3.setSourceOffice(sourceOffice);
        order3.setDestinationOffice(destinationOffice);

        when(orderService.findById(1)).thenReturn(Optional.of(order1));
        when(orderService.findById(2)).thenReturn(Optional.of(order2));
        when(orderService.findById(3)).thenReturn(Optional.of(order3));

        Driver driverMock = mock(Driver.class);
        Truck truckMock = mock(Truck.class);
        when(truckMock.getCapacity()).thenReturn(50.0);
        when(driverMock.getTruck()).thenReturn(truckMock);

        when(employeeService.findById(createDTO.getDriverId())
                .orElseThrow(() -> new RuntimeException("Employee not found")).getDriver()).thenReturn(driverMock);


        Shipment shipment = new Shipment();
        when(shipmentRepository.save(any())).thenReturn(shipment);

        when(shipmentMapper.toDto(any())).thenReturn(new ShipmentResponseDTO());

        ShipmentResponseDTO result = shipmentService.createNewShipment(createDTO);
        assertNotNull(result);
        verify(shipmentRepository).save(any());
    }

    @Test
    void shouldGetAssignedShipments() {
        Shipment shipmentMock = mock(Shipment.class);
        when(shipmentMock.getShipmentId()).thenReturn(1);
        when(shipmentRepository.findAllByDriver_EmployeeId(anyInt())).thenReturn(List.of(shipmentMock));

        DriverShipmentsResponseDTO result = shipmentService.getAssignedShipments();
        assertNotNull(result);
        assertEquals(1, result.getShipmentId().size());
        assertEquals(1, result.getShipmentId().get(0));
    }
}

