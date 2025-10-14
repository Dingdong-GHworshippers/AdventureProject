package dk.ek.adventureproject.dto;

import dk.ek.adventureproject.model.enums.Role;

public record EmployeeCreateDTO(String userName, String password, Role role) {
}
