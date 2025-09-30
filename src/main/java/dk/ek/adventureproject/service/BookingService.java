package dk.ek.adventureproject.service;

import dk.ek.adventureproject.Model.ActivityTimeslot;
import dk.ek.adventureproject.Model.Booking;
import dk.ek.adventureproject.Model.Customer;
import dk.ek.adventureproject.repo.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id){
        Optional<Booking> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isEmpty()){
            throw new RuntimeException("Customer not found with id "+id);
        }

        return optionalBooking.get();
    }

    public Booking createBooking(Booking booking){
        booking.setId(null);
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking booking){
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isEmpty()){
            throw new RuntimeException("Customer not found with id "+id);
        }

        Booking updatedBooking = optionalBooking.get();

        List<ActivityTimeslot> timeslots = updatedBooking.getActivityTimeslots();
        if (timeslots != null){
            for (var slots: timeslots){
                slots.setBooking(booking);
            }
            updatedBooking.setActivityTimeslots(timeslots);
        }

        updatedBooking.setBookingOrder(booking.getBookingOrder());
        updatedBooking.setDate(booking.getDate());
        updatedBooking.setCustomer(booking.getCustomer());

        return bookingRepository.save(updatedBooking);
    }

    public void deleteBooking(Long id){
        Optional<Booking> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isEmpty()){
            throw new RuntimeException();
        }

        Booking booking = optionalBooking.get();

        bookingRepository.delete(booking);
    }


}


