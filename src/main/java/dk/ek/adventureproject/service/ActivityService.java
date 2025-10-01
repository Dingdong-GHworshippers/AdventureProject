package dk.ek.adventureproject.service;


import dk.ek.adventureproject.Model.Activity;
import dk.ek.adventureproject.repo.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;


    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }


    public Activity getActivityById(Long id){
        Optional<Activity> activity = activityRepository.findById(id);
        if(activity.isPresent()){
            return activity.get();
        }
        return null;
    }


    public Activity createActivity(Activity activity) {
        activity.setId(null);
        return activityRepository.save(activity);
    }


    public Activity updateActivity(Activity activity, Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if(optionalActivity.isEmpty()){
            throw new RuntimeException("Activity not found with id " + id);
        }
        Activity updatedActivity = optionalActivity.get();
        updatedActivity.setActivityName(activity.getActivityName());
        updatedActivity.setActivityDescription(activity.getActivityDescription());
        updatedActivity.setActivityPrice(activity.getActivityPrice());

        return activityRepository.save(updatedActivity);
    }

    public void deleteActivity(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);

        if(optionalActivity.isEmpty()){
            throw new RuntimeException("Activity not found with id " + id);
        }

        Activity activity = optionalActivity.get();
        activityRepository.delete(activity);
    }

}
