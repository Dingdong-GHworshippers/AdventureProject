package dk.ek.adventureproject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class ActivityTimeslot {
    @Id
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ActivityTimeslot(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public ActivityTimeslot() {

    }
}
