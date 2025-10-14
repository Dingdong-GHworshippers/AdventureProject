package dk.ek.adventureproject.dto;

import dk.ek.adventureproject.model.enums.Role;

public record EmployeeDTO(Long id, String userName, Role role) {
}
