package dk.ek.adventureproject.Service;

import dk.ek.adventureproject.Model.Employee;
import dk.ek.adventureproject.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;


    // Create
    public Employee createEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    // get all
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    // get by id
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }

    // Update
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepo.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setUserName(employeeDetails.getUserName());
            employee.setPassword(employeeDetails.getPassword());
            employee.setRole(employeeDetails.getRole());

            return employeeRepo.save(employee);
        } else {
            throw new RuntimeException("Employee not found with id " + id);
        }
    }

    // Delete
    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

}
