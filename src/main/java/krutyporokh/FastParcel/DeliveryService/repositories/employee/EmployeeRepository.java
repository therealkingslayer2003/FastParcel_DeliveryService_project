package krutyporokh.FastParcel.DeliveryService.repositories.employee;

import krutyporokh.FastParcel.DeliveryService.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmail(String email);
}
