package dk.ek.adventureproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.ek.adventureproject.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Reverse relation to Timeslots, not sure if needed, could potentially be used to show an employees rosterplan.
    @ManyToMany(mappedBy = "employees")
    @JsonBackReference(value = "employee-timeslots")
    private List<ActivityTimeslot> activityTimeslots = new ArrayList<>();

    public Employee(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public Employee() {

    }
}
