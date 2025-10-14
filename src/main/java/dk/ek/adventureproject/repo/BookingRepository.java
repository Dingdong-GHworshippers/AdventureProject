package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
