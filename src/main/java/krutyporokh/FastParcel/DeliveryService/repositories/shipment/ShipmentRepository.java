package krutyporokh.FastParcel.DeliveryService.repositories.shipment;

import jakarta.persistence.criteria.CriteriaBuilder;
import krutyporokh.FastParcel.DeliveryService.models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    List<Shipment> findAllByDriver_EmployeeId(Integer employeeId);
}

