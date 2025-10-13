// This method iterates through all connected activityTimeslots and maps the activity names
// Since a booking can contain several activityTimeslots, we need to find the earliest startTime and latest end
export function renderBookings(bookings, tableBodyEl) {
    tableBodyEl.innerHTML = "";

    bookings.forEach(booking => {
        const bookingDate = booking.date;

        // Get activityTimeslots safely
        const timeslots = booking.activityTimeslots || [];

        // Collect activity names and make them clickable to access the activity timeslot page for said activity
        const activityLinks = timeslots.map(ts => {
            const name = ts.activity?.activityName || "Unknown";
            const id = ts.id;
            return `<a href="/activity-timeslot-page.html?id=${id}">${name}</a>`;
        }).join(", ");

        const startTimes = timeslots.map(ts => new Date(ts.startTime));
        const endTimes = timeslots.map(ts => new Date(ts.endTime));

        // Get earliest start and latest end
        const earliestStartTime = new Date(Math.min(...startTimes));
        const latestEndTime = new Date(Math.max(...endTimes));

        const formattedStartTime = formatTime(earliestStartTime);
        const formattedEndTime = formatTime(latestEndTime);


        const row = document.createElement("tr");
        row.dataset.bookingId = booking.id;
        row.innerHTML = `
            <td>${booking.id}</td>
            <td>${booking.customer.name}</td>
            <td>${booking.date}</td>
            <td>${formattedStartTime}</td>
            <td>${formattedEndTime}</td>
            <td>${activityLinks}</td>
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

// Fills booking form with existing date from booking field that is not activityTimeslot related
export function fillBookingForm(form, booking) {
    form.bookingId.value = booking.id || "";
    form.customerId.value = booking.customer?.id || "";
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