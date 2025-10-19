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
        return "activity-timeslot-page.html";
    }

    @GetMapping("/about-us")
    public String showAboutUsPage() {
        return "about-us.html";
    }

    @GetMapping("/admin-page")
    public String showAdminPage() {
        return "admin-page.html";
    }

    @GetMapping("/gallery")
    public String showGalleryPage() {
        return "gallery.html";
    }

    @GetMapping("/")
    public String showIndexPage() {
        return "index.html";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login.html";
    }

    @GetMapping("/oversee-products")
    public String showOverseeProductsPage() {
        return "oversee-products.html";
    }

    @GetMapping("/products")
    public String showProductsPage() {
        return "products.html";
    }

    @GetMapping("/roster-and-employees")
    public String showRosterAndEmployeesPage() {
        return "roster-and-employees.html";
    }

    @GetMapping("/see-activities")
    public String showSeeActivitiesPage() {
        return "see-activities.html";
    }

    @GetMapping("/see-bookings")
    public String showSeeBookingsPage() {
        return "see-bookings.html";
    }
    @GetMapping("/see-booking-order")
    public String showBookingOrderPage(){
        return "see-booking-order.html";
    }
}
