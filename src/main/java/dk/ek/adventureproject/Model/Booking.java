package dk.ek.adventureproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "bookings")
public class Booking {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    @OneToMany (mappedBy = "booking", cascade = CascadeType.ALL)
    private List<ActivityTimeslot> activityTimeslots = new ArrayList<>();

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_product_id" )
    private BookingOrder bookingOrder;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Booking(){}

    public Booking(Long id, LocalDate date, List<ActivityTimeslot> activityTimeslots, BookingOrder bookingOrder, Customer customer) {
        this.id = id;
        this.date = date;
        this.activityTimeslots = activityTimeslots;
        this.bookingOrder = bookingOrder;
        this.customer = customer;
    }
}
