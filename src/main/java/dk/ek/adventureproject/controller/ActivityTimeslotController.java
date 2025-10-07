package dk.ek.adventureproject.controller;


import dk.ek.adventureproject.Model.ActivityTimeslot;
import dk.ek.adventureproject.Service.ActivityTimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/timeslot")
@RequiredArgsConstructor
public class ActivityTimeslotController {

    private final ActivityTimeslotService activityTimeslotService;

    @GetMapping
    public ResponseEntity<List<ActivityTimeslot>> getAllActivityTimeslots(){
        return ResponseEntity.ok(activityTimeslotService.getAllActivityTimeslots());
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<ActivityTimeslot>> getTimeslotsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date){
        return ResponseEntity.ok(activityTimeslotService.getActivityTimeSlotsByDate(date));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityTimeslot> updateActivityTimeslot(@PathVariable Long id, @RequestBody ActivityTimeslot activityTimeslot) {
        try{
            return  ResponseEntity.ok(activityTimeslotService.updateActivityTimeslot(id, activityTimeslot));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityTimeslot(@PathVariable Long id){
        try{
            activityTimeslotService.deleteActivityTimeslot(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }


}
