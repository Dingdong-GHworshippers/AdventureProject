package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> getByNameContainingIgnoreCase(String name);
}
