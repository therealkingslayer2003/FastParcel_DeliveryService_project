package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.DTO.DriverDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.EmployeeDTO;
import krutyporokh.FastParcel.DeliveryService.models.Driver;
import krutyporokh.FastParcel.DeliveryService.models.Employee;
import krutyporokh.FastParcel.DeliveryService.models.EmployeeRole;
import krutyporokh.FastParcel.DeliveryService.models.Truck;
import krutyporokh.FastParcel.DeliveryService.repositories.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final OfficeRepository officeRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final DriverRepository driverRepository;
    private final TruckRepository truckRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerEmployee(EmployeeDTO employeeDto) throws ChangeSetPersister.NotFoundException {
        Employee employee = new Employee();

        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(bCryptPasswordEncoder.encode(employeeDto.getPassword()));  //Encoding a password
        employee.setName(employeeDto.getName());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());

        EmployeeRole employeeRole = employeeRoleRepository.findByEmployeeRoleName(employeeDto.getEmployeeRole())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee role"));
        employee.setEmployeeRole(employeeRole);

        employee.setOffice(officeRepository.findById(employeeDto.getOfficeId()).   //Getting the Office object by ID from
                orElseThrow(ChangeSetPersister.NotFoundException::new));

        employeeRepository.save(employee);
    }

    public void registerDriver(DriverDTO driverDTO) throws ChangeSetPersister.NotFoundException {
        registerEmployee(driverDTO);

        Driver driver = new Driver();

        Optional<Employee> employee = employeeRepository.findByEmail(driverDTO.getEmail());
        driver.setEmployee(employee.get());
        driver.setLicenseNumber(driverDTO.getLicenseNumber());

        driverRepository.save(driver);

        Truck truck = new Truck();
        truck.setModel(driverDTO.getModel());
        truck.setCapacity(driverDTO.getCapacity());
        truck.setDriver(driver);

        truckRepository.save(truck);
    }

}


//    @Transactional
//    public Optional<Employee> authenticate(EmployeeDTO employeeDto) {
//
//        Optional<Employee> employee = employeeRepository.findByEmail(employeeDto.getEmail());
//
//        if (employee.isPresent() && bCryptPasswordEncoder.matches(employeeDto.getPassword(), employee.get().getPassword())) {
//            return employee;
//        }
//        return Optional.empty();
//    }



