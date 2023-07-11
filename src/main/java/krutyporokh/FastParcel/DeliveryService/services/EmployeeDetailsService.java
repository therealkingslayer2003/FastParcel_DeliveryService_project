package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.models.Employee;
import krutyporokh.FastParcel.DeliveryService.repositories.EmployeeRepository;
import krutyporokh.FastParcel.DeliveryService.security.EmployeeDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new EmployeeDetails(employee.get());
    }
}