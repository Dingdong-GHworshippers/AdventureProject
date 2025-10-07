package dk.ek.adventureproject.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<ActivityTimeslot> activityTimeslots = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "booking_product_id" )
    private BookingOrder bookingOrder;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    private int minAge;

    public Booking(){}

    public Booking(Long id, LocalDate date, List<ActivityTimeslot> activityTimeslots, BookingOrder bookingOrder, Customer customer, int minAge) {
        this.id = id;
        this.date = date;
        this.activityTimeslots = activityTimeslots;
        this.bookingOrder = bookingOrder;
        this.customer = customer;
        this.minAge = minAge;
    }
}
