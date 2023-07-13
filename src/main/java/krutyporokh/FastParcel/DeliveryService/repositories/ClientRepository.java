package krutyporokh.FastParcel.DeliveryService.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import krutyporokh.FastParcel.DeliveryService.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
