package dk.ek.adventureproject.common;

import dk.ek.adventureproject.model.*;
import dk.ek.adventureproject.model.enums.ProductType;
import dk.ek.adventureproject.model.enums.Role;
import dk.ek.adventureproject.service.*;
import dk.ek.adventureproject.dto.EmployeeCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final ActivityService activityService;
    private final ActivityTimeslotService activityTimeslotService;
    private final BookingService bookingService;
    private final BookingOrderService bookingOrderService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {

        // --- PRODUCTS ---
        Product burger = new Product(0, ProductType.FOOD, "Burger" , 50.0);
        Product soda = new Product(0, ProductType.DRINK, "Sodavand", 15.0);
        Product tShirt = new Product(0, ProductType.MERCHANDISE, "T-shirt" , 150.0);
        productService.save(burger);
        productService.save(soda);
        productService.save(tShirt);

        // --- ACTIVITIES ---
        Activity gokart = new Activity(null, "GoKart", "GoKart i høj fart!", 200, 15);
        Activity minigolf = new Activity(null, "Mini golf", "Hvem er den bedste putter på golfbanen?", 80, 15);
        Activity paintball = new Activity(null, "Paintball", "Skyd hinanden og se krig med egne øjne!", 150, 15);
        Activity sumo = new Activity(null, "Sumo Wrestling", "Bliv tykke og bryd med hinanden", 50, 15);
        Activity bowling = new Activity(null, "Bowling", "Hvem kan smadre flest kegler med jeres kugler?", 80, 15);
        Activity curling = new Activity(null, "Curling", "Frem med kosten og puds skøjtebanen!", 150, 15);
        activityService.createActivity(gokart);
        activityService.createActivity(minigolf);
        activityService.createActivity(paintball);
        activityService.createActivity(sumo);
        activityService.createActivity(bowling);
        activityService.createActivity(curling);

        // --- EMPLOYEES ---
        Employee alice = employeeService.createEmployeeEntity(new EmployeeCreateDTO("Alice", "password1", Role.MANAGER));
        Employee bob = employeeService.createEmployeeEntity(new EmployeeCreateDTO("Bob", "password2", Role.EMPLOYEE));
        Employee carol = employeeService.createEmployeeEntity(new EmployeeCreateDTO("Carol", "password3", Role.EMPLOYEE));
        Employee david = employeeService.createEmployeeEntity(new EmployeeCreateDTO("David", "password4", Role.EMPLOYEE));
        Employee emma = employeeService.createEmployeeEntity(new EmployeeCreateDTO("Emma", "password5", Role.EMPLOYEE));
        Employee frederik = employeeService.createEmployeeEntity(new EmployeeCreateDTO("Frederik", "password6", Role.EMPLOYEE));
        Employee gustav = employeeService.createEmployeeEntity(new EmployeeCreateDTO("Gustav", "password7", Role.EMPLOYEE));
        Employee hannah = employeeService.createEmployeeEntity(new EmployeeCreateDTO("Hannah", "password8", Role.EMPLOYEE));
        Employee isabella = employeeService.createEmployeeEntity(new EmployeeCreateDTO("Isabella", "password9", Role.MANAGER));
        Employee jonas = employeeService.createEmployeeEntity(new EmployeeCreateDTO("Jonas", "password10", Role.EMPLOYEE));


        // --- CUSTOMERS ---
        Customer customer1 = customerService.createCustomer(
                new Customer(null, "John Doe", "john@example.com", "+4512345678", Set.of()));
        Customer customer2 = customerService.createCustomer(
                new Customer(null, "Jane Smith", "jane@example.com", "+4598765432", Set.of()));

        // --- BOOKINGS with ActivityTimeslots attached BEFORE saving ---

        // Setup booking 1
        BookingOrder order1 = new BookingOrder(null, 0.0, null, List.of(burger, soda));

        ActivityTimeslot paintballMorning = new ActivityTimeslot(
                null,
                LocalDateTime.now().minusDays(1).withHour(10).withMinute(0),
                LocalDateTime.now().minusDays(1).withHour(12).withMinute(0)
        );
        paintballMorning.setActivity(paintball);
        paintballMorning.getEmployees().add(bob);
        paintballMorning.setBooked(true);

        Booking booking1 = new Booking(
                null,
                LocalDate.now().minusDays(1),
                List.of(paintballMorning),
                order1,
                customer1,
                15
        );
        paintballMorning.setBooking(booking1);
        booking1 = bookingService.createBooking(booking1); // Will include the activity price


        // Setup booking 2
        BookingOrder order2 = new BookingOrder(null, 0.0, null, List.of(tShirt));

        ActivityTimeslot curlingAfternoon = new ActivityTimeslot(
                null,
                LocalDateTime.now().minusDays(2).withHour(12).withMinute(0),
                LocalDateTime.now().minusDays(2).withHour(14).withMinute(0)
        );
        curlingAfternoon.setActivity(curling);
        curlingAfternoon.setBooked(true);

        Booking booking2 = new Booking(
                null,
                LocalDate.now().minusDays(2),
                List.of(curlingAfternoon),
                order2,
                customer2,
                16
        );
        curlingAfternoon.setBooking(booking2);
        booking2 = bookingService.createBooking(booking2);

        activityTimeslotService.generateTimeslotsForNextMonth();
    }


}