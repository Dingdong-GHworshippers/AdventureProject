package dk.ek.adventureproject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
public class Activity {
    @Id
    private Long id;

    private String activityName;
    private String activityDescription;
    private double activityPrice;

    public Activity(Long id, String activityName, String activityDescription, double activityPrice) {
        this.id = id;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.activityPrice = activityPrice;

    }

    public Activity() {

    }
}
