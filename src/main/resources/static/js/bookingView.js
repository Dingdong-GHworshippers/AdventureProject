// This method iterates through all connected activityTimeslots and maps the activity names
export function renderBookings(bookings, tableBodyEl) {
    tableBodyEl.innerHTML = "";

    bookings.forEach(booking => {
        const activityName = booking.activityTimeslots.map(ts =>
            `${ts.activity?.activityName || "Unknown"}`
        ).join("<br>");

        const row = document.createElement("tr");
        row.dataset.bookingId = booking.id;
        row.innerHTML = `
            <td>${booking.date}</td>
            <td>${activityName}</td>
            <td>${booking.minAge}</td>
            <td>${booking.price}</td>
            <td>${booking.customer?.name || "N/A"}</td>
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
    form.date.value = booking.date || "";
    form.minAge.value = booking.minAge || "";
    form.price.value = booking.price || "";
    form.customerId.value = booking.customer?.id || "";
}

export function resetBookingForm(form) {
    form.reset();
}