package dk.ek.adventureproject.dto;

import java.time.LocalDateTime;

public record TimeslotSelectionDTO(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long activityId
) {}