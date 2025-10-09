package dk.ek.adventureproject.dto;

import java.util.List;

public record BookingRequestDTO(
        String name,
        String email,
        String phone,
        int minAge,
        List<TimeslotSelectionDTO> selectedTimeslots
) {}