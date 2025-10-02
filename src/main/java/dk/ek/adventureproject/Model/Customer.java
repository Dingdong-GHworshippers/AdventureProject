package dk.ek.adventureproject.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.List;
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

    @Pattern(
            regexp = "^\\+\\d{1,4}[-\\s]?\\d{4,15}$",
            message = "Phone number must include a country code and 4–15 digits (e.g. +45 12345678)"
    )
    String phoneNumber;

    @OneToMany (mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
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
