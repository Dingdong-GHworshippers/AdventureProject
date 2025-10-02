package dk.ek.adventureproject.Service;

import dk.ek.adventureproject.Model.Employee;
import dk.ek.adventureproject.dto.EmployeeCreateDTO;
import dk.ek.adventureproject.dto.EmployeeDTO;
import dk.ek.adventureproject.dto.Mapper;
import dk.ek.adventureproject.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    //using employeeCREATEdto instead, uses password
    public EmployeeDTO createEmployee(EmployeeCreateDTO dto) {
        Employee employee = Mapper.toEntity(dto);
        return Mapper.toDto(employeeRepo.save(employee));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeDTO> result = new ArrayList<>();
        for (Employee e : employees) {
            result.add(Mapper.toDto(e));
        }
        return result;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            return Mapper.toDto(employee.get());
        }
        throw new RuntimeException("Employee not found");
    }

    public void deleteEmployee(Long id) {
        if(employeeRepo.existsById(id)) {
            employeeRepo.deleteById(id);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

}
