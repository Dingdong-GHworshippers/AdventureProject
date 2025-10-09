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
}
