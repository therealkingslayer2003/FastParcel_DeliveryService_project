package krutyporokh.FastParcel.DeliveryService.controllers;

import jakarta.validation.Valid;
import krutyporokh.FastParcel.DeliveryService.DTO.DriverDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.EmployeeDTO;
import krutyporokh.FastParcel.DeliveryService.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SecurityController {
    private final EmployeeService employeeService;

    public SecurityController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/register/employee")
    public ResponseEntity<HttpStatus> registerEmployee(@Valid @RequestBody EmployeeDTO employeeDto) {
        try {
            employeeService.registerEmployee(employeeDto);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/register/driver")
    public ResponseEntity<HttpStatus> registerDriver(@Valid @RequestBody DriverDTO driverDTO) {
        try {
            employeeService.registerDriver(driverDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


//    @PostMapping("/login")
//    public ResponseEntity<HttpStatus> authenticate(@RequestBody EmployeeDTO employeeDto) {
//        try {
//            employeeService.authenticate(employeeDto);
//            return ResponseEntity.ok(HttpStatus.OK);
//        }  catch (Exception e) {
//            return ResponseEntity.status(401).build();
//        }
//    }
}
