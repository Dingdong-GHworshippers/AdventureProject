package dk.ek.adventureproject.repo;

import dk.ek.adventureproject.Model.Product;
import dk.ek.adventureproject.Model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByType(ProductType type);

}