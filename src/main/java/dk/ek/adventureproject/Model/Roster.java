package dk.ek.adventureproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Roster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "employee_id") // foreign key in roster table
    private Employee employee;

    public Roster() {

    }
    public Roster(LocalDate date, Employee employee) {
        this.date = date;
        this.employee = employee;
    }

}
