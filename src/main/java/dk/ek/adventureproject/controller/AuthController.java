package dk.ek.adventureproject.controller;

import dk.ek.adventureproject.service.EmployeeService;
import dk.ek.adventureproject.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final EmployeeService employeeService;

    // Login via JSON i body
    @PostMapping("/login")
    public EmployeeDTO login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        return employeeService.login(username, password);
    }
}