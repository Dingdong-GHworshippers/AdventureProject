package dk.ek.adventureproject.Service;

import dk.ek.adventureproject.Model.ActivityTimeslot;
import dk.ek.adventureproject.Model.Employee;
import dk.ek.adventureproject.repo.ActivityTimeslotRepository;
import dk.ek.adventureproject.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ActivityTimeslotService {

    private final ActivityTimeslotRepository activityTimeslotRepository;
    private final EmployeeRepo employeeRepo;

    public List<ActivityTimeslot> generateTimeslotsForDay(LocalDate date) {
        List<ActivityTimeslot> slots = new ArrayList<>();

        LocalDateTime start = date.atTime(10, 0);

        for (int i = 0; i < 4; i++) {
            ActivityTimeslot slot = new ActivityTimeslot();
            slot.setStartTime(start.plusHours(i * 2));
            slot.setEndTime(start.plusHours(i * 2 + 2)); // Sets end time for two hours after start time for said timeslot
            slot.setEmployees(List.of());
            slot.setBooking(null);
            slot.setId(null);
            slot.setBooked(false);
            slots.add(slot);
        }

        return slots;
    }

    public void generateTimeslotsForNextMonth() {
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 30; i++) {
            LocalDate day = today.plusDays(i);
            List<ActivityTimeslot> slots = generateTimeslotsForDay(day);
            for (ActivityTimeslot ts : slots){
                createActivityTimeslot(ts);
            }
        }
    }

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

    public List<ActivityTimeslot> getActivityTimeSlotsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23,59,59);
        return activityTimeslotRepository.findByStartTimeBetween(startOfDay, endOfDay);
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
        updatedActivityTimeslot.setBooked(activityTimeslot.isBooked());
        updatedActivityTimeslot.setEmployees(activityTimeslot.getEmployees());
        updatedActivityTimeslot.setActivity(activityTimeslot.getActivity());

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

    public ActivityTimeslot assignEmployeeToTimeslot(Long timeslotId, Long employeeId) {
        ActivityTimeslot ts = activityTimeslotRepository.findById(timeslotId)
                .orElseThrow(() -> new RuntimeException("Timeslot not found"));
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!ts.getEmployees().contains(emp)) {
            ts.getEmployees().add(emp);
        }
        return activityTimeslotRepository.save(ts);
    }

    public ActivityTimeslot unassignEmployeeFromTimeslot(Long timeslotId, Long employeeId) {
        ActivityTimeslot ts = activityTimeslotRepository.findById(timeslotId)
                .orElseThrow(() -> new RuntimeException("Timeslot not found"));
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        ts.getEmployees().remove(emp);
        return activityTimeslotRepository.save(ts);
    }
}
