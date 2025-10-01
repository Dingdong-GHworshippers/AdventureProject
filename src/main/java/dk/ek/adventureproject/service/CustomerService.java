package dk.ek.adventureproject.Service;

import dk.ek.adventureproject.Model.Booking;
import dk.ek.adventureproject.Model.Customer;
import dk.ek.adventureproject.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public List<Customer> getCustomerByName(String name){
        return customerRepository.getByNameContainingIgnoreCase(name);
    }

    public Customer getCustomerById(Long id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        return null;
    }

    public Customer createCustomer(Customer customer){
        customer.setId(null);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()){
            throw new RuntimeException("Customer with id "+id+" not found");
        }
        Customer updatedCustomer = optionalCustomer.get();
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
        updatedCustomer.setEmail(customer.getEmail());

        Set<Booking> bookings = customer.getBookings();
        if (bookings != null){
            for (var booking : bookings){
                booking.setCustomer(updatedCustomer);
            }
            updatedCustomer.setBookings(bookings);
        }


        return customerRepository.save(updatedCustomer);
    }

    public void deleteCustomer(Long id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()){
            throw new RuntimeException("Customer with id "+id+" not found");
        }

        Customer customer = optionalCustomer.get();
        customerRepository.delete(customer);
    }
}
