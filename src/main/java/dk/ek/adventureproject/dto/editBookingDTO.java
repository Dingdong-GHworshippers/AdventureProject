package dk.ek.adventureproject.dto;

import java.time.LocalDate;

public record editBookingDTO(Long customerId,
                             LocalDate date,
                             int minAge,
                             double price) {
}
