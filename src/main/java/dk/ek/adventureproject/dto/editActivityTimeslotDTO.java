package dk.ek.adventureproject.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record editActivityTimeslotDTO(
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long activityId
) {
}
