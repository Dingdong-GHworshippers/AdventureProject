package dk.ek.adventureproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table (name = "booking_orders")
public class BookingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double total;

    @OneToOne
    @JoinColumn (name = "booking_id")
    @JsonBackReference
    private Booking booking;

    @ManyToMany
    @JoinTable(
            name = "booking_order_product",
            joinColumns = @JoinColumn(name = "booking_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<Product> products = new ArrayList<>();

    public BookingOrder(Long id, double total, Booking booking, List<Product> products) {
        this.id = id;
        this.total = total;
        this.booking = booking;
        this.products = products;
    }

    public BookingOrder(){}
}
