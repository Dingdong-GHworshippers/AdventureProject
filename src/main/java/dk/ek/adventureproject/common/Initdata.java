package dk.ek.adventureproject.common;

import dk.ek.adventureproject.Model.Activity;
import dk.ek.adventureproject.Model.Employee;
import dk.ek.adventureproject.Model.Roster;
import dk.ek.adventureproject.Model.enums.Role;
import dk.ek.adventureproject.repo.ActivityRepository;
import dk.ek.adventureproject.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Initdata implements CommandLineRunner {

    private final EmployeeRepo employeeRepo;
    private final ActivityRepository repo;


    @Override
    public void run(String... args) throws Exception {

        // Create employees
        Employee alice = new Employee("Alice", "secret123", Role.MANAGER);
        Employee bob   = new Employee("Bob", "hunter2", Role.EMPLOYEE);

        //Create rosters and assign to employees via helper method
        Roster roster1 = new Roster(LocalDate.of(2025, 9, 29), null);
        Roster roster2 = new Roster(LocalDate.of(2025, 9, 30), null);
        Roster roster3 = new Roster(LocalDate.of(2025, 10, 1), null);

        alice.addRoster(roster1);
        alice.addRoster(roster2);
        bob.addRoster(roster3);

        // Persist employees (cascade = ALL will save rosters too)
        employeeRepo.save(alice);
        employeeRepo.save(bob);

        repo.save(new Activity(1L, "Gokart", "Race mod dine venner", 299.0));
        repo.save(new Activity(2L, "Paintball", "Fuld fart på farverne", 249.0));
        repo.save(new Activity(3L, "Sumobrydning", "Kæmp i oppustelige dragter", 199.0));
    }
}
