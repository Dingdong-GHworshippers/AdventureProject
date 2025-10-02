package dk.ek.adventureproject.Model;

import dk.ek.adventureproject.Model.enums.ProductType;
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

    public Product(long id, ProductType type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public Product(){}

    public void setId(long id) {
        this.id = id;
    }
}
