package krutyporokh.FastParcel.DeliveryService.repositories;

import krutyporokh.FastParcel.DeliveryService.models.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Integer> {
}
