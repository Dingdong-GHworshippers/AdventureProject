package dk.ek.adventureproject.repositories;

import dk.ek.adventureproject.Model.Roster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RosterRepo extends JpaRepository<Roster, Long> {
}
