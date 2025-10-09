// This method iterates through all connected activityTimeslots and maps the activity names
// Since a booking can contain several activityTimeslots, we need to find the earliest startTime and latest endTime
export function renderBookings(bookings, tableBodyEl) {
    tableBodyEl.innerHTML = "";

    bookings.forEach(booking => {
        const bookingDate = booking.date;

        // Get activityTimeslots safely
        const timeslots = booking.activityTimeslots || [];

        // Collect activity names and times
        const activityNames = timeslots.map(ts => ts.activity?.activityName || "Unknown").join(", ");
        const startTimes = timeslots.map(ts => new Date(ts.startTime));
        const endTimes = timeslots.map(ts => new Date(ts.endTime));

        // Get earliest start and latest end
        const earliestStartTime = new Date(Math.min(...startTimes));
        const latestEndTime = new Date(Math.max(...endTimes));

        const formattedStartTime = formatTime(earliestStartTime);
        const formattedEndTime = formatTime(latestEndTime);

        // Output (for DOM or console)
        console.log(`Booking on ${bookingDate}`);
        console.log(`Activity: ${activityNames}`);
        console.log(`Time: ${formattedStartTime} - ${formattedEndTime}`);
        console.log(`Min age: ${booking.minAge}, Price: ${booking.price}`);


        const row = document.createElement("tr");
        row.dataset.bookingId = booking.id;
        row.innerHTML = `
            <td>${booking.customer.name}</td>
            <td>${booking.date}</td>
            <td>${formattedStartTime}</td>
            <td>${formattedEndTime}</td>
            <td>${activityNames}</td>
            <td>${booking.minAge}</td>
            <td>${booking.price}</td>
           
            <td>
                <button class="edit-booking-btn">Edit</button>
                <button class="delete-booking-btn">Delete</button>
            </td>
        `;
        tableBodyEl.appendChild(row);
    });
}

// ======== NOT FINISED Unsure how the form should display each value, need to return to this one=======
export function fillBookingForm(form, booking) {
    form.customerId.value = booking.customer?.id || "";
    form.date.value = booking.date || "";
    form.date.value = booking.date || "";
    form.minAge.value = booking.minAge || "";
    form.price.value = booking.price || "";

}

export function resetBookingForm(form) {
    form.reset();
}

function formatTime(date) {
    const h = date.getHours().toString().padStart(2, "0");
    const m = date.getMinutes().toString().padStart(2, "0");
    return `${h}:${m}`;
}