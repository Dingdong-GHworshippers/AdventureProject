package dk.ek.adventureproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/customers")
    public String showCustomersPage(){
        return "customers-page.html";
    }

    @GetMapping("/bookings")
    public String showBookingsPage(){
        return "bookings-page.html";
    }

    @GetMapping("/make-booking")
    public String makeBookingPage(){
        return "make-booking-page.html";
    }

    @GetMapping("/activity-timeslots")
    public String showActivityTimeslots(){
        return "activity-timeslot-page.html";
    }

    @GetMapping("/activity-timeslots-page/{id}")
    public String showTimeslotDetailsPage() {
        return "activity-timeslot-page";
    }

    @GetMapping("/see-booking-order")
    public String showBookingOrderPage(){
        return "see-booking-order.html";
    }

    @GetMapping("/see-booking-order/{id}")
    public String showBookingOrderById(){
        return "see-booking-order.html";
    }


}
