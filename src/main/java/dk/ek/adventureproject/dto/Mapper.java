package dk.ek.adventureproject.dto;

import dk.ek.adventureproject.Model.Employee;

public class Mapper {

    public static EmployeeDTO toDto(Employee employee) {
        if (employee == null) return null;
        return new EmployeeDTO(
                employee.getId(),
                employee.getUserName(),
                employee.getRole()
        );
    }

    public static Employee toEntity(EmployeeCreateDTO dto) {
        if (dto == null) return null;
        Employee employee = new Employee();
        employee.setUserName(dto.userName());
        employee.setPassword(dto.password()); // will be hashed in service
        employee.setRole(dto.role());
        return employee;
    }
}
