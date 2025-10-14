package dk.ek.adventureproject.controller;

import dk.ek.adventureproject.model.*;
import dk.ek.adventureproject.service.ActivityTimeslotService;
import dk.ek.adventureproject.service.BookingService;
import dk.ek.adventureproject.service.CustomerService;
import dk.ek.adventureproject.dto.BookingRequestDTO;
import dk.ek.adventureproject.dto.Mapper;
import dk.ek.adventureproject.dto.editBookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final Mapper mapper;
    private final CustomerService customerService;
    private final ActivityTimeslotService activityTimeslotService;

    // Returns all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Returns by id - should probably have a search function instead to search all parameters
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        Booking booking = bookingService.getBookingById(id);
        if (booking == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(booking);
    }

    /* Method for creating a booking
    It receives a bookingRequestDTO that contains the customer info, booking info and activity timeslot info.
    As the Booking class cascades down to the ActivityTimeslots, we do not need to save them as well,
    but we do need to add the customer and activity timeslots to the booking, and we need to set the booking on each
    activity timeslot converted from the bookingRequestDTO.
    */
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO){
        Customer customer = mapper.requestDtoToCustomer(bookingRequestDTO);
        customerService.createCustomer(customer);

        List<ActivityTimeslot> timeslots = mapper.timeslotSelectionDtoToActivityTimeslot(bookingRequestDTO.selectedTimeslots());

        Booking booking = new Booking();
        booking.setDate(timeslots.getFirst().getStartTime().toLocalDate());
        booking.setCustomer(customer);

        for (ActivityTimeslot ts : timeslots) {
            ts.setBooking(booking);
            ts.setBooked(true);
        }

        booking.setActivityTimeslots(timeslots);
        booking.setMinAge(bookingRequestDTO.minAge());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
    }

    //Updates by id
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody editBookingDTO editBookingDTO){
        if (bookingService.getBookingById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Booking updatedBooking = bookingService.updateBooking(id, editBookingDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    // Deletes by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable Long id){
        if (bookingService.getBookingById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        bookingService.deleteBooking(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
