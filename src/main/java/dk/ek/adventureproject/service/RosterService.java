package dk.ek.adventureproject.service;

import dk.ek.adventureproject.model.Employee;
import dk.ek.adventureproject.model.Roster;
import dk.ek.adventureproject.repo.EmployeeRepo;
import dk.ek.adventureproject.repo.RosterRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class RosterService {

    private final RosterRepo rosterRepo;
    private final EmployeeRepo employeeRepo;
    

    // Create
    public Roster createRoster(Roster roster) {
        return rosterRepo.save(roster);
    }

    // get all
    public List<Roster> getAllRosters() {
        return rosterRepo.findAll();
    }

    // get by id
    public Optional<Roster> getRosterById(Long id) {
        return rosterRepo.findById(id);
    }

    // Update
    public Roster updateRoster(Long id, Roster rosterDetails) {
        Optional<Roster> optionalRoster = rosterRepo.findById(id);

        if (optionalRoster.isPresent()) {
            Roster roster = optionalRoster.get();
            roster.setDate(rosterDetails.getDate());
            roster.setEmployee(rosterDetails.getEmployee());
            roster.setShiftStart(rosterDetails.getShiftStart());
            roster.setShiftEnd(rosterDetails.getShiftEnd());
            return rosterRepo.save(roster);
        } else {
            throw new RuntimeException("Roster not found with id " + id);
        }
    }

    // Delete
    public void deleteRoster(Long id) {
        rosterRepo.deleteById(id);
    }

    // ADD employee to roster (helper method)
    public Roster addEmployeeToRoster(Long rosterId, Long employeeId) {
        Optional<Roster> optionalRoster = rosterRepo.findById(rosterId);
        Optional<Employee> optionalEmployee = employeeRepo.findById(employeeId);

        if (optionalRoster.isPresent() && optionalEmployee.isPresent()) {
            Roster roster = optionalRoster.get();
            Employee employee = optionalEmployee.get();

            roster.setEmployee(employee); // assign employee to this roster
            return rosterRepo.save(roster);
        } else {
            throw new RuntimeException("Roster or Employee not found");
        }
    }

    // CREATE roster for employee on a given date
    public Roster createRosterForEmployee(Long employeeId, LocalDate date, LocalTime start, LocalTime end) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));

        Roster roster = new Roster(date, start, end, employee);
        return rosterRepo.save(roster);
    }



    public List<Roster> getRosterByDate(LocalDate date) {
        return rosterRepo.findByDate(date);
    }

    public Roster saveRoster(Roster roster) {
        return rosterRepo.save(roster);
    }
}
