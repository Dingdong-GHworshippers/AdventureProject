package dk.ek.adventureproject.controller;

import dk.ek.adventureproject.model.Customer;
import dk.ek.adventureproject.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Returns all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // Takes id as a parameter but also handled in below method. Unsure if necessary
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer (@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);
        if (customer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customer);
        }

        return ResponseEntity.ok(customer);
    }

    // Adds a search endpoint so one can search for name. Can be extended for email, phone, etc
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name) {
        if (id != null){
            Customer result = customerService.getCustomerById(id);
            return ResponseEntity.ok(List.of(result));
        }
        if (name != null && !name.isBlank()) {
            List<Customer> result = customerService.getCustomerByName(name);
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.ok(customerService.getAllCustomers());
        }
    }

    // Creates customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer){

        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    // Updates customer. Throws not found status if invalid id
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer){
        if (customerService.getCustomerById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    // Deletes customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);
        if (customer == null){
            return ResponseEntity.notFound().build();
        }

        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
