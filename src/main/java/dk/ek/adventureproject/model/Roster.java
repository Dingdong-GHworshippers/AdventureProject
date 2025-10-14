package dk.ek.adventureproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table (name = "roster")
public class Roster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime shiftStart;
    private LocalTime shiftEnd;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference(value = "employee-rosters")
    private Employee employee;

    public Roster() {

    }
    public Roster(LocalDate date, LocalTime shiftStart, LocalTime shiftEnd, Employee employee) {
        this.date = date;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.employee = employee;
    }

}
