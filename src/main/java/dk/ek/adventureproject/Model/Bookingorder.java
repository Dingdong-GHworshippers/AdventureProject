package dk.ek.adventureproject.Model;

import jakarta.persistence.*;

@Entity
public class Bookingorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double total;

    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
