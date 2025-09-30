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

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ActivityTimeslot(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public ActivityTimeslot() {

    }
}
