package dk.ek.adventureproject.dto;

import dk.ek.adventureproject.Model.ActivityTimeslot;
import dk.ek.adventureproject.Model.Booking;
import dk.ek.adventureproject.Model.Customer;
import dk.ek.adventureproject.Model.Employee;
import dk.ek.adventureproject.Service.ActivityService;
import dk.ek.adventureproject.Service.ActivityTimeslotService;
import dk.ek.adventureproject.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class Mapper {

    private final ActivityService activityService;
    private final ActivityTimeslotService activityTimeslotService;
    private final BookingService bookingService;

    public Mapper(ActivityService activityService, ActivityTimeslotService activityTimeslotService, BookingService bookingService) {
        this.activityService = activityService;
        this.activityTimeslotService = activityTimeslotService;
        this.bookingService = bookingService;
    }

    public EmployeeDTO toDto(Employee employee) {
        if (employee == null) return null;
        return new EmployeeDTO(
                employee.getId(),
                employee.getUserName(),
                employee.getRole()
        );
    }

    public Employee toEntity(EmployeeCreateDTO dto) {
        if (dto == null) return null;
        Employee employee = new Employee();
        employee.setUserName(dto.userName());
        employee.setPassword(dto.password()); // will be hashed in service
        employee.setRole(dto.role());
        return employee;
    }

    public Customer requestDtoToCustomer(BookingRequestDTO bookingRequestDTO){
        Customer customer = new Customer();
        customer.setName(bookingRequestDTO.name());
        customer.setEmail(bookingRequestDTO.email());
        customer.setPhoneNumber(bookingRequestDTO.phone());
        return customer;
    }

    /*
    Takes each timeslot activity DTO from the list of activity timeslot DTOs and maps them to the correct timeslot
    from the database, so that the setBooked change is persisted throughout them all.
    */
    public List<ActivityTimeslot> timeslotSelectionDtoToActivityTimeslot(List<TimeslotSelectionDTO> timeslotSelectionDTOList){
        List<ActivityTimeslot> timeslots = new ArrayList<>();

        for (TimeslotSelectionDTO tsDto : timeslotSelectionDTOList){
            ActivityTimeslot ts = activityTimeslotService.getActivityTimeslotById(tsDto.id());
            ts.setActivity(activityService.getActivityById(tsDto.activityId()));
            ts.setStartTime(tsDto.startTime());
            ts.setEndTime(tsDto.endTime());
            ts.setBooked(true);
            ts.setEmployees(List.of());
            timeslots.add(ts);
        }
        return timeslots;
    }

    /*
    public Booking editBookingDtoToBooking(Booking existingBooking, editBookingDTO editBookingDTO){
        existingBooking.setDate(editBookingDTO.date());
        existingBooking.setMinAge(editBookingDTO.minAge());
        existingBooking.setPrice(editBookingDTO.price());

        return existingBooking;
    }*/



}
