package dk.ek.adventureproject.controller;

import dk.ek.adventureproject.Model.Roster;
import dk.ek.adventureproject.Service.RosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/roster")
@RequiredArgsConstructor
public class RosterController {

    private final RosterService rosterService;

    // CREATE
    @PostMapping
    public Roster createRoster(@RequestBody Roster roster) {
        return rosterService.createRoster(roster);
    }

    // READ - all
    @GetMapping
    public List<Roster> getAllRosters() {
        return rosterService.getAllRosters();
    }

    // READ - by id
    @GetMapping("/{id}")
    public Roster getRosterById(@PathVariable Long id) {
        return rosterService.getRosterById(id)
                .orElseThrow(() -> new RuntimeException("Roster not found with id " + id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Roster updateRoster(@PathVariable Long id, @RequestBody Roster rosterDetails) {
        return rosterService.updateRoster(id, rosterDetails);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteRoster(@PathVariable Long id) {
        rosterService.deleteRoster(id);
    }

    // ASSIGN an employee to a roster
    @PostMapping("/{rosterId}/employee/{employeeId}")
    public Roster addEmployeeToRoster(@PathVariable Long rosterId, @PathVariable Long employeeId) {
        return rosterService.addEmployeeToRoster(rosterId, employeeId);
    }

    // CREATE roster for employee on a given date
    @PostMapping("/employee/{employeeId}")
    public Roster createRosterForEmployee(@PathVariable Long employeeId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return rosterService.createRosterForEmployee(employeeId, localDate);
    }
}
