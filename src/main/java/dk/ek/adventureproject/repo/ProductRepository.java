package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dk.ek.adventureproject.model.enums.ProductType;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByType(ProductType type);

}