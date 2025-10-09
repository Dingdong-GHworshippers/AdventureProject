import { renderTimeslots, collectFormData } from "./makeBookingView.js";
import { getTimeslotsByDate } from "./api/activityTimeslotApi.js";

const timeslotList = document.getElementById("timeslot-list");
const dateInput = document.getElementById("booking-date");
const form = document.getElementById("booking-form");

// Sets the date input for todays date default
dateInput.value = new Date().toISOString().split("T")[0];


// Fetches the activities to display in the drop down menu
async function fetchActivities() {
    const res = await fetch("/api/activities");
    return res.json();
}

// Loads the timeslots depending on the entered date
async function loadUIForDate(dateStr) {
    const [timeslots, activities] = await Promise.all([
        getTimeslotsByDate(dateStr),
        fetchActivities()
    ]);
    // Filters for timeslots that are not booked and not before current time and date
    const availableTimeslots = timeslots.filter(ts => ts.booked === false && new Date(ts.startTime) > new Date());

    renderTimeslots(availableTimeslots, timeslotList, activities);
}

// Listens for a date change in the date field
dateInput.addEventListener("change", () => {
    loadUIForDate(dateInput.value);
});

// Submits booking and checks that everything has been entered correctly
form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const bookingData = collectFormData(dateInput.value);

    if (
        !bookingData.name ||
        !bookingData.email ||
        !bookingData.phone ||
        !bookingData.minAge ||
        bookingData.selectedTimeslots.length === 0
    ) {
        alert("Alle felter og minimum én tid skal vælges");
        return;
    }

    if (bookingData.selectedTimeslots.some(ts => !ts.activityId)) {
        alert("Du skal vælge en aktivitet for hvert tidsrum");
        return;
    }

    // Could have used POST method from bookingApi, but the fetch method does not return res.ok, so it never reloads and gives success message
    try {
        const res = await fetch("/api/bookings", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(bookingData),
        });

        if (res.ok) {
            alert("Booking successful!");
            form.reset();
            dateInput.dispatchEvent(new Event("change"));
        } else {
            alert("Booking fejlet, prøv igen");
        }
    } catch (err) {
        console.error(err);
    }
});

// Initial load
loadUIForDate(dateInput.value);