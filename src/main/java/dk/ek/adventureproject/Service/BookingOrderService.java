package dk.ek.adventureproject.Service;

import dk.ek.adventureproject.Model.BookingOrder;
import dk.ek.adventureproject.Model.Product;
import dk.ek.adventureproject.repo.BookingOrderRepo;
import dk.ek.adventureproject.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingOrderService {

    private final BookingOrderRepo bookingOrderRepository;
    private final ProductRepository productRepository;

    public BookingOrderService(BookingOrderRepo bookingOrderRepository, ProductRepository productRepository) {
        this.bookingOrderRepository = bookingOrderRepository;
        this.productRepository = productRepository;
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
        BookingOrder order = bookingOrderRepository.findById(id).orElse(null);

        List<Product> products = order.getProducts();
        for (Product product : products) {
            products.remove(product);
        }

        bookingOrderRepository.deleteById(id);
    }


    public boolean existsById(Long id) {
        return bookingOrderRepository.existsById(id);
    }

    public BookingOrder addProductToOrder(Long orderId, Long productId) {
        BookingOrder order = bookingOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("BookingOrder ikke fundet"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt ikke fundet"));

        order.getProducts().add(product);
        order.setTotal(order.getTotal() + product.getPrice());
        return bookingOrderRepository.save(order);
    }

    public BookingOrder removeProductFromOrder(Long orderId, Long productId) {
        BookingOrder order = bookingOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("BookingOrder ikke fundet"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt ikke fundet"));

        order.getProducts().remove(product);
        order.setTotal(order.getProducts().stream().mapToDouble(Product::getPrice).sum());
        return bookingOrderRepository.save(order);
    }
}