package dk.ek.adventureproject.Service;

import dk.ek.adventureproject.Model.BookingOrder;
import dk.ek.adventureproject.repo.BookingOrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingOrderService {

    private final BookingOrderRepo bookingOrderRepository;

    public BookingOrderService(BookingOrderRepo bookingOrderRepository) {
        this.bookingOrderRepository = bookingOrderRepository;
    }


    public List<BookingOrder> findAll() {
        return bookingOrderRepository.findAll();
    }


    public Optional<BookingOrder> findById(Long id) {
        return bookingOrderRepository.findById(id);
    }


    public BookingOrder save(BookingOrder bookingOrder) {
        return bookingOrderRepository.save(bookingOrder);
    }


    public void deleteById(Long id) {
        bookingOrderRepository.deleteById(id);
    }
}
