package dk.ek.adventureproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table (name = "activity_timeslots")
public class ActivityTimeslot {
    @Id
    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn (name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn (name = "booking_id")
    private Booking booking;

    public ActivityTimeslot(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public ActivityTimeslot() {

    }
}
