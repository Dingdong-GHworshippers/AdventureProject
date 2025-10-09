package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.Model.ActivityTimeslot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ActivityTimeslotRepository extends JpaRepository<ActivityTimeslot,Long> {

    List<ActivityTimeslot> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

}
