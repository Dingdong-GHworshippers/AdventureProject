package dk.ek.adventureproject.controller;

import dk.ek.adventureproject.model.ActivityTimeslot;
import dk.ek.adventureproject.service.ActivityTimeslotService;
import dk.ek.adventureproject.dto.editActivityTimeslotDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/timeslot")
@RequiredArgsConstructor
public class ActivityTimeslotController {

    private final ActivityTimeslotService activityTimeslotService;

    //get alle activity timeslots
    @GetMapping
    public ResponseEntity<List<ActivityTimeslot>> getAllActivityTimeslots(){
        return ResponseEntity.ok(activityTimeslotService.getAllActivityTimeslots());
    }

    //get a specific timeslot by id
    @GetMapping("/{id}")
    public ResponseEntity<ActivityTimeslot> getTimeslotById(@PathVariable Long id){
        return ResponseEntity.ok(activityTimeslotService.getActivityTimeslotById(id));
    }

    //get by date
    @GetMapping("/by-date")
    public ResponseEntity<List<ActivityTimeslot>> getTimeslotsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(activityTimeslotService.getActivityTimeSlotsByDate(date));
    }

    //update timeslot
    @PutMapping("/{id}")
    public ResponseEntity<ActivityTimeslot> updateActivityTimeslot(@PathVariable Long id, @RequestBody editActivityTimeslotDTO activityTimeslotDTO) {
        try{
            return  ResponseEntity.ok(activityTimeslotService.updateActivityTimeslot(id, activityTimeslotDTO));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //delete timeslot
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityTimeslot(@PathVariable Long id){
        try{
            activityTimeslotService.deleteActivityTimeslot(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //assign an employee to a timeslot
    @PostMapping("/{id}/assign")
    public ResponseEntity<ActivityTimeslot> assignEmployeeToTimeslot(
            @PathVariable Long id,
            @RequestBody Long employeeId
    ) {
        ActivityTimeslot updated = activityTimeslotService.assignEmployeeToTimeslot(id, employeeId);
        return ResponseEntity.ok(updated);
    }
}
