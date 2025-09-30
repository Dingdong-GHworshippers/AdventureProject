package dk.ek.adventureproject.service;

import dk.ek.adventureproject.Model.ActivityTimeslot;
import dk.ek.adventureproject.repo.ActivityTimeslotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ActivityTimeslotService {

    private final ActivityTimeslotRepository activityTimeslotRepository;

    public List<ActivityTimeslot> getAllActivityTimeslots() {
        return activityTimeslotRepository.findAll();
    }

    public ActivityTimeslot getActivityTimeslotById(Long id) {
        Optional<ActivityTimeslot> OptionalActivityTimeslot = activityTimeslotRepository.findById(id);
        if (OptionalActivityTimeslot.isPresent()) {
            return OptionalActivityTimeslot.get();
        }
        return null;
    }

    public List<ActivityTimeslot> getActivityTimeSlotsByDate(LocalDateTime date) {
        return activityTimeslotRepository.findByStartTimeBetween(date.toLocalDate().atStartOfDay(), date.toLocalDate().atTime(23,59,59));
    }

    public ActivityTimeslot createActivityTimeslot(ActivityTimeslot activityTimeslot) {
        activityTimeslot.setId(null);
        return activityTimeslotRepository.save(activityTimeslot);
    }

    public ActivityTimeslot updateActivityTimeslot(Long id, ActivityTimeslot activityTimeslot) {
        Optional<ActivityTimeslot> OptionalActivityTimeslot = activityTimeslotRepository.findById(id);
        if (OptionalActivityTimeslot.isEmpty()) {
            throw new RuntimeException("ActivityTimeslot Not Found with id " + id);
        }
        ActivityTimeslot updatedActivityTimeslot = OptionalActivityTimeslot.get();
        updatedActivityTimeslot.setStartTime(activityTimeslot.getStartTime());
        updatedActivityTimeslot.setEndTime(activityTimeslot.getEndTime());

        return activityTimeslotRepository.save(updatedActivityTimeslot);
    }

    public void deleteActivityTimeslot(Long id) {
        Optional<ActivityTimeslot> OptionalActivityTimeslot = activityTimeslotRepository.findById(id);

        if (OptionalActivityTimeslot.isEmpty()) {
            throw new RuntimeException("ActivityTimeslot Not Found with id " + id);
        }
        ActivityTimeslot activityTimeslot = OptionalActivityTimeslot.get();
        activityTimeslotRepository.delete(activityTimeslot);
    }


}
