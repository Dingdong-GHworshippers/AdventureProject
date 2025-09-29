package dk.ek.adventureproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    String name;
    String email;
    int phoneNumber;

    @OneToMany (mappedBy = "customer")
    private Set<Booking> bookings = new HashSet<>();


}
