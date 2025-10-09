package dk.ek.adventureproject.controller;

import dk.ek.adventureproject.Model.Booking;
import dk.ek.adventureproject.Model.BookingOrder;
import dk.ek.adventureproject.Service.BookingService;
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

    // Creates booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
    }

    //Updates by id
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking){
        if (bookingService.getBookingById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Booking updatedBooking = bookingService.updateBooking(id, booking);
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
