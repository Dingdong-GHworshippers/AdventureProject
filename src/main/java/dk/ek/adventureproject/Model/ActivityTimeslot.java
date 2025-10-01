package dk.ek.adventureproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "activity_timeslots")
public class ActivityTimeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
  


    @ManyToOne
    @JoinColumn (name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn (name = "booking_id")
    private Booking booking;


    @ManyToMany
    @JoinTable(
            name = "activity_timeslot_employees",
            joinColumns = @JoinColumn(name = "timeslot_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees = new ArrayList<>();


    public ActivityTimeslot(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ActivityTimeslot() {
    }
  
}
