package dk.ek.adventureproject.common;

import dk.ek.adventureproject.Model.*;
import dk.ek.adventureproject.Model.enums.ProductType;
import dk.ek.adventureproject.Model.enums.Role;
import dk.ek.adventureproject.Service.*;
import dk.ek.adventureproject.dto.EmployeeCreateDTO;
import dk.ek.adventureproject.dto.EmployeeDTO;
import dk.ek.adventureproject.dto.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final RosterService rosterService;

    @Override
    public void run(String... args) throws Exception {
        // --- PRODUCTS ---
        Product burger = new Product(0, ProductType.FOOD, "Burger" , 15.0);
        Product soda = new Product(0, ProductType.DRINK, "Sodavand", 5.0);
        Product tShirt = new Product(0, ProductType.MERCHANDISE, "T-shirt" , 25.0);
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
        Employee alice = employeeService.createEmployeeEntity(new EmployeeCreateDTO("alice", "password1", Role.MANAGER));
        Employee bob = employeeService.createEmployeeEntity(new EmployeeCreateDTO("bob", "password2", Role.EMPLOYEE));
        Employee carol = employeeService.createEmployeeEntity(new EmployeeCreateDTO("carol", "password3", Role.EMPLOYEE));

        // --- CUSTOMERS ---
        Customer customer1 = customerService.createCustomer(
                new Customer(null, "John Doe", "john@example.com", "+4512345678", Set.of()));
        Customer customer2 = customerService.createCustomer(
                new Customer(null, "Jane Smith", "jane@example.com", "+4598765432", Set.of()));

        // --- BOOKINGS with ActivityTimeslots attached BEFORE saving ---

        // Setup booking 1
        BookingOrder order1 = new BookingOrder(null, 0.0, null, List.of(burger, soda));
        bookingOrderService.save(order1);

        ActivityTimeslot paintballMorning = new ActivityTimeslot(
                null,
                LocalDateTime.now().plusDays(1).withHour(9).withMinute(0),
                LocalDateTime.now().plusDays(1).withHour(11).withMinute(0)
        );
        paintballMorning.setActivity(paintball);
        paintballMorning.getEmployees().add(bob);

        Booking booking1 = new Booking(
                null,
                LocalDate.now().plusDays(1),
                List.of(paintballMorning),
                order1,
                customer1,
                14
        );
        paintballMorning.setBooking(booking1);
        booking1 = bookingService.createBooking(booking1); // Will include the activity price

        activityTimeslotService.createActivityTimeslot(paintballMorning); // Optional if no cascade

        // Setup booking 2
        BookingOrder order2 = new BookingOrder(null, 0.0, null, List.of(tShirt));
        bookingOrderService.save(order2);

        ActivityTimeslot curlingAfternoon = new ActivityTimeslot(
                null,
                LocalDateTime.now().plusDays(2).withHour(13).withMinute(0),
                LocalDateTime.now().plusDays(2).withHour(15).withMinute(0)
        );
        curlingAfternoon.setActivity(curling);
        curlingAfternoon.getEmployees().add(carol);

        Booking booking2 = new Booking(
                null,
                LocalDate.now().plusDays(2),
                List.of(curlingAfternoon),
                order2,
                customer2,
                16
        );
        curlingAfternoon.setBooking(booking2);
        booking2 = bookingService.createBooking(booking2);

        activityTimeslotService.createActivityTimeslot(curlingAfternoon); // Optional if cascade

        // --- ROSTERS ---
        Roster roster1 = new Roster(LocalDate.now().plusDays(1), alice);
        Roster roster2 = new Roster(LocalDate.now().plusDays(2), bob);
        Roster roster3 = new Roster(LocalDate.now().plusDays(3), carol);
        rosterService.createRoster(roster1);
        rosterService.createRoster(roster2);
        rosterService.createRoster(roster3);
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        // --- PRODUCTS ---
//        Product burger = new Product(0, ProductType.FOOD, "Burger" , 15.0);
//        Product soda = new Product(0, ProductType.DRINK, "Sodavand", 5.0);
//        Product tShirt = new Product(0, ProductType.MERCHANDISE, "T-shirt" ,25.0);
//        productService.save(burger);
//        productService.save(soda);
//        productService.save(tShirt);
//
//        // --- ACTIVITIES ---
//        Activity gokart = new Activity(null, "GoKart", "GoKart i høj fart!", 200, 15);
//        Activity minigolf = new Activity(null, "Mini golf", "Hvem er den bedste putter på golfbanen?", 80, 15);
//        Activity paintball = new Activity(null, "Paintball", "Skyd hinanden og se krig med egne øjne!", 150, 15);
//        Activity sumo = new Activity(null, "Sumo Wrestling", "Bliv tykke og bryd med hinanden", 50, 15);
//        Activity bowling = new Activity(null, "Bowling", "Hvem kan smadre flest kegler med jeres kugler?", 80, 15);
//        Activity curling = new Activity(null, "Curling", "Frem med kosten og puds skøjtebanen!", 150, 15);
//        activityService.createActivity(gokart);
//        activityService.createActivity(minigolf);
//        activityService.createActivity(paintball);
//        activityService.createActivity(sumo);
//        activityService.createActivity(bowling);
//        activityService.createActivity(curling);
//
//        // --- EMPLOYEES ---
//        EmployeeCreateDTO aliceDTO = new EmployeeCreateDTO("alice", "password1", Role.MANAGER);
//        EmployeeCreateDTO bobDTO = new EmployeeCreateDTO("bob", "password2", Role.EMPLOYEE);
//        EmployeeCreateDTO carolDTO = new EmployeeCreateDTO("carol", "password3", Role.EMPLOYEE);
//
//        Employee alice = employeeService.createEmployeeEntity(aliceDTO);
//        Employee bob = employeeService.createEmployeeEntity(bobDTO);
//        Employee carol = employeeService.createEmployeeEntity(carolDTO);
//
//        // --- CUSTOMERS ---
//        Customer customer1 = new Customer(null, "John Doe", "john@example.com", "+4512345678", Set.of());
//        Customer customer2 = new Customer(null, "Jane Smith", "jane@example.com", "+4598765432", Set.of());
//        customer1 = customerService.createCustomer(customer1);
//        customer2 = customerService.createCustomer(customer2);
//
//        // --- BOOKINGS & BOOKING ORDERS ---
//        BookingOrder order1 = new BookingOrder(null, 95.0, null, List.of(burger, soda));
//        BookingOrder order2 = new BookingOrder(null, 160.0, null, List.of(tShirt));
//
//        bookingOrderService.save(order1);
//        bookingOrderService.save(order2);
//
//        Booking booking1 = new Booking(null, LocalDate.now().plusDays(1), null, order1, customer1, 14);
//        Booking booking2 = new Booking(null, LocalDate.now().plusDays(2), null, order2, customer2, 16);
//        booking1 = bookingService.createBooking(booking1);
//        booking2 = bookingService.createBooking(booking2);
//
//        // --- ACTIVITY TIMESLOTS ---
//        ActivityTimeslot paintballMorning = new ActivityTimeslot(null,
//                LocalDateTime.now().plusDays(1).withHour(9).withMinute(0),
//                LocalDateTime.now().plusDays(1).withHour(11).withMinute(0));
//        paintballMorning.setActivity(paintball);
//        paintballMorning.setBooking(booking1);
//        paintballMorning.getEmployees().add(bob);
//
//        ActivityTimeslot curlingAfternoon = new ActivityTimeslot(null,
//                LocalDateTime.now().plusDays(2).withHour(13).withMinute(0),
//                LocalDateTime.now().plusDays(2).withHour(15).withMinute(0));
//        curlingAfternoon.setActivity(curling);
//        curlingAfternoon.setBooking(booking2);
//        curlingAfternoon.getEmployees().add(carol);
//
//        activityTimeslotService.createActivityTimeslot(paintballMorning);
//        activityTimeslotService.createActivityTimeslot(curlingAfternoon);
//
//        booking1.setActivityTimeslots(List.of(paintballMorning));
//        booking2.setActivityTimeslots(List.of(curlingAfternoon));
//
//        // --- ROSTERS ---
//        Roster roster1 = new Roster(LocalDate.now().plusDays(1), alice);
//        Roster roster2 = new Roster(LocalDate.now().plusDays(2), bob);
//        Roster roster3 = new Roster(LocalDate.now().plusDays(3), carol);
//
//        rosterService.createRoster(roster1);
//        rosterService.createRoster(roster2);
//        rosterService.createRoster(roster3);
//    }
}