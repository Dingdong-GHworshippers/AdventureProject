package dk.ek.adventureproject.Model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private double price;

    @OneToMany
    private List<Bookingorder> bookingorders;

}
 enum ProductType{
    FOOD,DRINK, MERCHANDISE,

}
