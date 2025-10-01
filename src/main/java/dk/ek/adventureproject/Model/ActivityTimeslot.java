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

    private LocalDateTime startTime;
    private LocalDateTime endTime;
  
    public ActivityTimeslot(Long id, LocalDateTime startTime, LocalDateTime endTime) {
      this.id = id;
      this.startTime = startTime;
      this.endTime = endTime;
    }

    @ManyToOne
    @JoinColumn (name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn (name = "booking_id")
    private Booking booking;

    public ActivityTimeslot() {
    }
  
}
