package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUserNameAndPassword(String userName, String password);
}

