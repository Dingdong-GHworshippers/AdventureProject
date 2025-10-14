package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
