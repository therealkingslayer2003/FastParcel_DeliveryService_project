package krutyporokh.FastParcel.DeliveryService.controllers;

import jakarta.validation.Valid;
import krutyporokh.FastParcel.DeliveryService.DTO.DriverCreateDTO;
import krutyporokh.FastParcel.DeliveryService.DTO.EmployeeCreateDTO;
import krutyporokh.FastParcel.DeliveryService.security.JwtTokenProvider;
import krutyporokh.FastParcel.DeliveryService.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")         //For Registration/Log in
@AllArgsConstructor
public class SecurityController {
    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register/employee")
    public ResponseEntity<HttpStatus> registerEmployee(@Valid @RequestBody EmployeeCreateDTO employeeCreateDto) {
        try {
            employeeService.registerEmployee(employeeCreateDto);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/register/driver")
    public ResponseEntity<HttpStatus> registerDriver(@Valid @RequestBody DriverCreateDTO driverDTO) {
        try {
            employeeService.registerDriver(driverDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody EmployeeCreateDTO employeeCreateDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            employeeCreateDto.getEmail(),
                            employeeCreateDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok("Logged in successfully, generated token: " + jwt);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: invalid email or password");
        }
    }


}
