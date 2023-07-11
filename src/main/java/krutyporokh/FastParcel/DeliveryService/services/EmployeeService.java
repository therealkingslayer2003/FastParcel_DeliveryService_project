package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.EmployeeDto;
import krutyporokh.FastParcel.DeliveryService.models.Employee;
import krutyporokh.FastParcel.DeliveryService.models.EmployeeRole;
import krutyporokh.FastParcel.DeliveryService.repositories.EmployeeRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.EmployeeRoleRepository;
import krutyporokh.FastParcel.DeliveryService.repositories.OfficeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final OfficeRepository officeRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void register(EmployeeDto employeeDto) throws ChangeSetPersister.NotFoundException {
        Employee employee = new Employee();

        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(bCryptPasswordEncoder.encode(employeeDto.getPassword()));  //Encoding a password
        employee.setName(employeeDto.getName());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());

        EmployeeRole role = employeeRoleRepository.findByEmployeeRoleName(employeeDto.getEmployeeRole())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee role"));
        employee.setEmployeeRole(role);

        employee.setOffice(officeRepository.findById(employeeDto.getOfficeId()).   //Getting the Office object by ID from
                orElseThrow(ChangeSetPersister.NotFoundException::new));

        employeeRepository.save(employee);
    }


//    @Transactional
//    public Optional<Employee> authenticate(EmployeeDto employeeDto) {
//
//        Optional<Employee> employee = employeeRepository.findByEmail(employeeDto.getEmail());
//
//        if (employee.isPresent() && bCryptPasswordEncoder.matches(employeeDto.getPassword(), employee.get().getPassword())) {
//            return employee;
//        }
//        return Optional.empty();
//    }


}