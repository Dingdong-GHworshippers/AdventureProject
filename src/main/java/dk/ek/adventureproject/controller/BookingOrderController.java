package dk.ek.adventureproject.controller;

import dk.ek.adventureproject.model.BookingOrder;
import dk.ek.adventureproject.model.Product;
import dk.ek.adventureproject.service.BookingOrderService;
import dk.ek.adventureproject.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/booking-orders")
public class BookingOrderController {

    private final BookingOrderService bookingOrderService;
    private final ProductService productService;

    public BookingOrderController(BookingOrderService bookingOrderService, ProductService productService) {
        this.bookingOrderService = bookingOrderService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<BookingOrder>> getAllBookingOrders() {
        List<BookingOrder> orders = bookingOrderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingOrder> getBookingOrderById(@PathVariable Long id) {
        return bookingOrderService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<BookingOrder> createBookingOrder(@RequestBody BookingOrder bookingOrder) {
        BookingOrder saved = bookingOrderService.save(bookingOrder);
        return ResponseEntity.created(URI.create("/api/booking-orders/" + saved.getId()))
                .body(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookingOrder> updateBookingOrder(@PathVariable Long id, @RequestBody BookingOrder bookingOrder) {
        if (!bookingOrderService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookingOrder.setId(id);
        BookingOrder updated = bookingOrderService.save(bookingOrder);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingOrder(@PathVariable Long id) {
        if (!bookingOrderService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookingOrderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{orderId}/products/{productId}")
    public ResponseEntity<BookingOrder> addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        BookingOrder order = bookingOrderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("BookingOrder not found"));
        Product product = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        order.getProducts().add(product);
        order.setTotal(order.getProducts().stream().mapToDouble(Product::getPrice).sum());
        BookingOrder updated = bookingOrderService.save(order);

        return ResponseEntity.ok(updated);
    }

    // ⭐ NYT: Fjern produkt fra bookingorder
    @DeleteMapping("/{orderId}/products/{productId}")
    public ResponseEntity<BookingOrder> removeProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        BookingOrder order = bookingOrderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("BookingOrder not found"));
        Product product = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        order.getProducts().remove(product);
        order.setTotal(order.getProducts().stream().mapToDouble(Product::getPrice).sum());
        BookingOrder updated = bookingOrderService.save(order);

        return ResponseEntity.ok(updated);
    }
}
