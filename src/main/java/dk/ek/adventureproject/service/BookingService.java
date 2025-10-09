package dk.ek.adventureproject.Service;

import dk.ek.adventureproject.Model.ActivityTimeslot;
import dk.ek.adventureproject.Model.Booking;
import dk.ek.adventureproject.Model.Customer;
import dk.ek.adventureproject.Model.Product;
import dk.ek.adventureproject.repo.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public boolean isOfAge(Booking booking){
        int activityMinAge = 20;
        for (ActivityTimeslot ts : booking.getActivityTimeslots()){
            if (ts.getActivity().getMinAge()<activityMinAge){
                activityMinAge = ts.getActivity().getMinAge();
            }
        }

        return booking.getMinAge() > activityMinAge;
    }

    public double calculateBookingPrice(Booking booking){
        List<ActivityTimeslot> timeslots = booking.getActivityTimeslots();
        List<Product> products = booking.getBookingOrder().getProducts();

        double activityPrices = 0;
        double bookingPrices = 0;

        if (timeslots != null){
            for (ActivityTimeslot ts : timeslots){
                activityPrices += ts.getActivity().getActivityPrice();
                System.out.println(activityPrices);
            }
            System.out.println(activityPrices);
        }

        if (products != null){
            for (Product p : products){
                bookingPrices += p.getPrice();
                System.out.println(bookingPrices);
            }
            System.out.println(bookingPrices);
        }

        return activityPrices + bookingPrices;
    }

    // Returns all bookings
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    // Returns booking by booking id
    public Booking getBookingById(Long id){
        Optional<Booking> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isEmpty()){
            throw new RuntimeException("Customer not found with id "+id);
        }

        return optionalBooking.get();
    }

    // Creates booking and sets id to null as it is auto-incremented
    public Booking createBooking(Booking booking){
        booking.setId(null);
        booking.setPrice(calculateBookingPrice(booking));
        if (!isOfAge(booking)){
            throw new RuntimeException("Den yngste deltager er ikke gammel nok til denne aktivitet");
        }
        return bookingRepository.save(booking);
    }

    // Updates booking and iterates through timeslots and updates them aswell
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
        updatedBooking.setMinAge(booking.getMinAge());
        updatedBooking.setPrice(calculateBookingPrice(updatedBooking));

        return bookingRepository.save(updatedBooking);
    }

    // Deletes booking
    public void deleteBooking(Long id){
        Optional<Booking> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isEmpty()){
            throw new RuntimeException();
        }

        Booking booking = optionalBooking.get();

        bookingRepository.delete(booking);
    }




}


