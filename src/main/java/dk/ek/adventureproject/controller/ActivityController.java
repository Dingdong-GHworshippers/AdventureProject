package dk.ek.adventureproject.controller;


import dk.ek.adventureproject.Model.Activity;
import dk.ek.adventureproject.Service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Activities")
@RequiredArgsConstructor

public class ActivityController {

    private final ActivityService activityService;


    //Returns all activities
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities(){
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    //Fetches an activity by id
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id){
        Activity activity = activityService.getActivityById(id);
        if(activity == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activity);
    }

    //Creates new activity
    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity){
        return ResponseEntity.ok(activityService.createActivity(activity));
    }


    //Updates existing activity
    @PutMapping
    public ResponseEntity<Activity> updateActivity(@RequestBody Activity activity, @PathVariable Long id){
        try {
            return ResponseEntity.ok(activityService.updateActivity(activity, id));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    //Deletes activity by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        try{
            activityService.deleteActivity(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }


}
