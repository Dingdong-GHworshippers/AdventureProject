package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.Model.Booking;
import dk.ek.adventureproject.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
