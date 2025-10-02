package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.Model.BookingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingOrderRepo extends JpaRepository<BookingOrder, Long> {
}