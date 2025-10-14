package dk.ek.adventureproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table (name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activityName;
    private String activityDescription;
    private double activityPrice;
    private int minAge;

    public Activity(Long id, String activityName, String activityDescription, double activityPrice, int minAge) {
        this.id = id;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.activityPrice = activityPrice;
        this.minAge = minAge;
    }

    public Activity() {

    }
}
