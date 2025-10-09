import { renderTimeslots, collectFormData } from "./makeBookingView.js";
import { getTimeslotsByDate } from "./api/activityTimeslotApi.js";

const timeslotList = document.getElementById("timeslot-list");
const dateInput = document.getElementById("booking-date");
const form = document.getElementById("booking-form");

dateInput.value = new Date().toISOString().split("T")[0];

async function fetchActivities() {
    const res = await fetch("/api/activities");
    return res.json();
}

async function loadUIForDate(dateStr) {
    const [timeslots, activities] = await Promise.all([
        getTimeslotsByDate(dateStr),
        fetchActivities()
    ]);
    // Filters for timeslots that are not booked and not before current time and date
    const availableTimeslots = timeslots.filter(ts => ts.booked === false && new Date(ts.startTime) > new Date());

    renderTimeslots(availableTimeslots, timeslotList, activities);
}

dateInput.addEventListener("change", () => {
    loadUIForDate(dateInput.value);
});

// Submit booking
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