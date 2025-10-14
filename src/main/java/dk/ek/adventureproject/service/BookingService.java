package dk.ek.adventureproject.service;

import dk.ek.adventureproject.model.*;
import dk.ek.adventureproject.dto.editBookingDTO;
import dk.ek.adventureproject.repo.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerService customerService;

    public boolean isOfAge(Booking booking){
        int activityMinAge = 20;
        for (ActivityTimeslot ts : booking.getActivityTimeslots()){
            if (ts.getActivity().getMinAge()<activityMinAge){
                activityMinAge = ts.getActivity().getMinAge();
            }
        }
        return booking.getMinAge() >= activityMinAge;
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
        BookingOrder bookingOrder = new BookingOrder();
        bookingOrder.setId(null);
        bookingOrder.setBooking(booking);

        booking.setId(null);
        booking.setBookingOrder(bookingOrder);

        for (ActivityTimeslot ts : booking.getActivityTimeslots()) {
            ts.setBooked(true);
            ts.setBooking(booking);
        }

        booking.setPrice(calculateBookingPrice(booking));

        if (!isOfAge(booking)){
            throw new RuntimeException("Den yngste deltager er ikke gammel nok til denne aktivitet");
        }
        return bookingRepository.save(booking);
    }

    // Updates booking from DTO
    public Booking updateBooking(Long id, editBookingDTO booking){
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isEmpty()){
            throw new RuntimeException("Booking not found with id "+id);
        }

        Booking existingBooking = optionalBooking.get();

        if (booking.customerId() != null) {
            Customer customer = customerService.getCustomerById(booking.customerId());
            if (customer == null) {
                throw new RuntimeException("Customer not found with id " + booking.customerId());
            }
            existingBooking.setCustomer(customer);
        }

        existingBooking.setDate(booking.date());
        existingBooking.setMinAge(booking.minAge());
        existingBooking.setPrice(booking.price());

        return bookingRepository.save(existingBooking);
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


