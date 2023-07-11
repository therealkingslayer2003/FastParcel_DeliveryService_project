package krutyporokh.FastParcel.DeliveryService.controllers;

import krutyporokh.FastParcel.DeliveryService.DTO.EmployeeDto;
import krutyporokh.FastParcel.DeliveryService.models.Employee;
import krutyporokh.FastParcel.DeliveryService.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SecurityController {
    private final EmployeeService employeeService;

    public SecurityController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody EmployeeDto employeeDto) {
        try {
            employeeService.register(employeeDto);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<HttpStatus> authenticate(@RequestBody EmployeeDto employeeDto) {
//        try {
//            employeeService.authenticate(employeeDto);
//            return ResponseEntity.ok(HttpStatus.OK);
//        }  catch (Exception e) {
//            return ResponseEntity.status(401).build();
//        }
//    }
}
