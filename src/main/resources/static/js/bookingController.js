import {getBookings, createBooking, updateBooking, deleteBooking} from "./api/bookingApi.js";

import {renderBookings, fillBookingForm, resetBookingForm} from "./bookingView.js";

const tableBody = document.querySelector("#bookings-table tbody");
const form = document.getElementById("booking-form");

let bookings = [];
let editBookingId = null;

document.addEventListener("DOMContentLoaded", async () => {
    await loadAndRenderBookings();
});

async function loadAndRenderBookings() {
    try {
        bookings = await getBookings();
        renderBookings(bookings, tableBody);
    } catch (err) {
        console.error("Kunne ikke loade bookinger", err);
    }
}

tableBody.addEventListener("click", async (event) => {
    const row = event.target.closest("tr");
    if (!row) return;

    const bookingId = row.dataset.bookingId;
    const booking = bookings.find(b => b.id == bookingId);

    if (event.target.classList.contains("edit-booking-btn")) {
        if (!booking) return;
        fillBookingForm(form, booking);
        editBookingId = booking.id;
    }

    if (event.target.classList.contains("delete-booking-btn")) {
        try {
            await deleteBooking(bookingId);
            bookings = bookings.filter(b => b.id != bookingId);
            renderBookings(bookings, tableBody);
        } catch (err) {
            console.error("Kunne ikke slette booking", err);
        }
    }
});

form.addEventListener("submit", async (event) => {
    event.preventDefault();

    // ============ NOT FINISHED, UNSURE HOW FORM SHOULD LOOK ============
    // const bookingData = {
    //     date: form.date.value,
    //     minAge: parseInt(form.minAge.value),
    //     price: parseFloat(form.price.value),
    //     customer: {
    //         id: parseInt(form.customerId.value)
    //     }
    // };

    try {
        if (editBookingId) {
            await updateBooking(editBookingId, bookingData);
            bookings = bookings.map(b => b.id == editBookingId ? { ...b, ...bookingData } : b);
            editBookingId = null;
        } else {
            const newBooking = await createBooking(bookingData);
            bookings.push(newBooking);
        }
        renderBookings(bookings, tableBody);
        resetBookingForm(form);
    } catch (err) {
        console.error("Kunne ikke slette/oprette booking", err);
    }
});