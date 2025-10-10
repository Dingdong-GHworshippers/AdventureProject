package dk.ek.adventureproject.Service;

import dk.ek.adventureproject.Model.Employee;
import dk.ek.adventureproject.dto.EmployeeCreateDTO;
import dk.ek.adventureproject.dto.EmployeeDTO;
import dk.ek.adventureproject.dto.Mapper;
import dk.ek.adventureproject.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final Mapper mapper;

    //using employeeCREATEdto instead, uses password
    public EmployeeDTO createEmployee(EmployeeCreateDTO dto) {
        Employee employee = mapper.toEntity(dto);
        return mapper.toDto(employeeRepo.save(employee));
    }

    public Employee createEmployeeEntity(EmployeeCreateDTO dto) {
        Employee employee = mapper.toEntity(dto);
        return employeeRepo.save(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeDTO> result = new ArrayList<>();
        for (Employee e : employees) {
            result.add(mapper.toDto(e));
        }
        return result;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            return mapper.toDto(employee.get());
        }
        throw new RuntimeException("Employee not found");
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }

        try {
            employeeRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Kan ikke slette bruger: Bruger tilhøre aktivitet");
        }
    }

    public EmployeeDTO login(String username, String password) {
        return employeeRepo.findByUserNameAndPassword(username, password)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Forkert brugernavn eller adgangskode"));
    }



}
