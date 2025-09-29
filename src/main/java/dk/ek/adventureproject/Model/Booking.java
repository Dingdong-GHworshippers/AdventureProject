package dk.ek.adventureproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private List<TimeSlot> timeSlots;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_product_id" )
    private BookingOrder bookingOrder;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
