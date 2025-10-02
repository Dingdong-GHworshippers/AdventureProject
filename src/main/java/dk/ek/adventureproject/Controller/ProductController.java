package dk.ek.adventureproject.Controller;

import dk.ek.adventureproject.Model.Product;
import dk.ek.adventureproject.Model.enums.ProductType;
import dk.ek.adventureproject.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow();
    }

    @GetMapping("/type/{type}")
    public List<Product> getProductsByType(@PathVariable ProductType type) {
        return productService.findByType(type);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }
}