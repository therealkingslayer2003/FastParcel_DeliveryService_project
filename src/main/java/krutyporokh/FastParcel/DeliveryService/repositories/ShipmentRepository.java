package krutyporokh.FastParcel.DeliveryService.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import krutyporokh.FastParcel.DeliveryService.models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

}
