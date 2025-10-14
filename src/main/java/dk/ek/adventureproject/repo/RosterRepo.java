package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.model.Roster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RosterRepo extends JpaRepository<Roster, Long> {
    List<Roster> findByDate(LocalDate date);

}
