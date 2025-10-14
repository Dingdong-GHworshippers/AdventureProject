package dk.ek.adventureproject.controller;

import dk.ek.adventureproject.model.Product;
import dk.ek.adventureproject.model.enums.ProductType;
import dk.ek.adventureproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // get all
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // get by type enum
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Product>> getProductsByType(@PathVariable ProductType type) {
        List<Product> products = productService.findByType(type);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // create product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        Product updatedProduct = productService.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}