package krutyporokh.FastParcel.DeliveryService.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import krutyporokh.FastParcel.DeliveryService.models.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Integer> {
    Optional<EmployeeRole> findByEmployeeRoleName(String employeeRoleName);
}
