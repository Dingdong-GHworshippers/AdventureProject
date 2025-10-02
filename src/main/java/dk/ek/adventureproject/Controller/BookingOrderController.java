package dk.ek.adventureproject.Controller;

import dk.ek.adventureproject.Model.BookingOrder;
import dk.ek.adventureproject.Service.BookingOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-orders")
public class BookingOrderController {

    private final BookingOrderService bookingOrderService;

    public BookingOrderController(BookingOrderService bookingOrderService) {
        this.bookingOrderService = bookingOrderService;
    }

    @GetMapping
    public List<BookingOrder> getAllBookingOrders() {
        return bookingOrderService.findAll();
    }

    @GetMapping("/{id}")
    public BookingOrder getBookingOrderById(@PathVariable Long id) {
        return bookingOrderService.findById(id).orElseThrow();
    }

    @PostMapping
    public BookingOrder createBookingOrder(@RequestBody BookingOrder bookingOrder) {
        return bookingOrderService.save(bookingOrder);
    }

    @PutMapping("/{id}")
    public BookingOrder updateBookingOrder(@PathVariable Long id, @RequestBody BookingOrder bookingOrder) {
        bookingOrder.setId(id);
        return bookingOrderService.save(bookingOrder);
    }

    @DeleteMapping("/{id}")
    public void deleteBookingOrder(@PathVariable Long id) {
        bookingOrderService.deleteById(id);
    }
}