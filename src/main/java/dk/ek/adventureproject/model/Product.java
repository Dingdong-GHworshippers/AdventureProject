package dk.ek.adventureproject.model;

import dk.ek.adventureproject.model.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private String name;

    private double price;

    public Product(long id, ProductType type, String name, double price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public Product(){}


}
