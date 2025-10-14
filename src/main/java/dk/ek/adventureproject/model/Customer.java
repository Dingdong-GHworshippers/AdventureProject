package dk.ek.adventureproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table (name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Name is required")
    String name;

    @Email
    String email;

    String phoneNumber;

    @OneToMany (mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Booking> bookings = new HashSet<>();

    public Customer(){}

    public Customer(Long id, String name, String email, String phoneNumber, Set<Booking> bookings) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bookings = bookings;
    }
}
