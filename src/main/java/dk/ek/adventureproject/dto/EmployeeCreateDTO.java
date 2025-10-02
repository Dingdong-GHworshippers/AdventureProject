package dk.ek.adventureproject.dto;

import dk.ek.adventureproject.Model.enums.Role;

public record EmployeeCreateDTO(String userName, String password, Role role) {
}
