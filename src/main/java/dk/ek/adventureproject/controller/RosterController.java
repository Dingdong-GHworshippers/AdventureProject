package dk.ek.adventureproject.Controller;


import dk.ek.adventureproject.Model.Roster;
import dk.ek.adventureproject.Service.RosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/roster")
@RequiredArgsConstructor
public class RosterController {

    private final RosterService rosterService;

    // CREATE
    @PostMapping
    public ResponseEntity<Roster> createRoster(@RequestBody Roster roster) {
        Roster created = rosterService.createRoster(roster);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ - all
    @GetMapping
    public ResponseEntity<List<Roster>> getAllRosters() {
        return ResponseEntity.ok(rosterService.getAllRosters());
    }

    // READ - by id
    @GetMapping("/{id}")
    public ResponseEntity<Roster> getRosterById(@PathVariable Long id) {
        return rosterService.getRosterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ - by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Roster>> getRosterForDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(rosterService.getRosterByDate(date));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Roster> updateRoster(@PathVariable Long id, @RequestBody Roster rosterDetails) {
        try {
            Roster updated = rosterService.updateRoster(id, rosterDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoster(@PathVariable Long id) {
        try {
            rosterService.deleteRoster(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // CREATE roster for specific employee and date
    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<Roster> createRosterForEmployee(
            @PathVariable Long employeeId,
            @RequestBody Roster roster) {
        LocalDate date = roster.getDate();
        LocalTime start = roster.getShiftStart();
        LocalTime end = roster.getShiftEnd();
        Roster created = rosterService.createRosterForEmployee(employeeId, date, start, end);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
