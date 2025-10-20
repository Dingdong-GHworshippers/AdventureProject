import {getBookings, createBooking, updateBooking, deleteBooking} from "./api/bookingApi.js";

import {renderBookings, fillBookingForm, resetBookingForm} from "./bookingView.js";

import {getCustomers} from "./api/customerApi.js";

const tableBody = document.querySelector("#bookings-table tbody");
const form = document.getElementById("booking-form");
const customerSelect = document.getElementById("customerId");
const searchInput = document.getElementById("search-input");

let bookings = [];
let editBookingId = null;
let filteredBookings = [];

document.addEventListener("DOMContentLoaded", async () => {
    await loadAndRenderBookings();
    await populateCustomersDropdown();
    setupSearchListener();
});

async function loadAndRenderBookings() {
    try {
        bookings = await getBookings();
        filteredBookings = [...bookings]; // Initialize filteredBookings
        renderBookings(filteredBookings, tableBody);
    } catch (err) {
        console.error("Kunne ikke loade bookinger", err);
    }
}

function setupSearchListener() {
    searchInput.addEventListener("input", () => {
        const searchTerm = searchInput.value.toLowerCase().trim();

        // If search is empty, show all bookings
        if (searchTerm === '') {
            filteredBookings = [...bookings];
        } else {
            // Filter bookings based on multiple fields
            filteredBookings = bookings.filter(booking => {
                // Extract search-relevant information
                const customerName = booking.customer ? booking.customer.name.toLowerCase() : '';
                const activityName = booking.activityTimeslots &&
                booking.activityTimeslots[0] &&
                booking.activityTimeslots[0].activity
                    ? booking.activityTimeslots[0].activity.activityName.toLowerCase()
                    : '';

                // Convert all values to searchable strings
                const searchFields = [
                    booking.id.toString().toLowerCase(),
                    customerName,
                    booking.date.toLowerCase(),
                    booking.minAge.toString().toLowerCase(),
                    booking.price.toString().toLowerCase(),
                    activityName
                ];

                // Check if any field includes the search term
                return searchFields.some(field => field.includes(searchTerm));
            });
        }

        // Render filtered bookings
        renderBookings(filteredBookings, tableBody);
    });
}

tableBody.addEventListener("click", async (event) => {
    const row = event.target.closest("tr");
    if (!row) return;

    const bookingId = row.dataset.bookingId;
    const booking = filteredBookings.find(b => b.id == bookingId);

    if (event.target.classList.contains("edit-booking-btn")) {
        if (!booking) return;
        fillBookingForm(form, booking);
        editBookingId = booking.id;
    }

    if (event.target.classList.contains("delete-booking-btn")) {
        try {
            await deleteBooking(bookingId);
            // Remove from both original and filtered bookings
            bookings = bookings.filter(b => b.id != bookingId);
            filteredBookings = filteredBookings.filter(b => b.id != bookingId);
            renderBookings(filteredBookings, tableBody);
        } catch (err) {
            console.error("Kunne ikke slette booking", err);
        }
    }
});

form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const bookingData = {
        customerId: parseInt(form.customerId.value),
        date: form.date.value,
        minAge: parseInt(form.minAge.value),
        price: parseFloat(form.price.value),
    };

    try {
        if (editBookingId) {
            // Update existing booking
            await updateBooking(editBookingId, bookingData);

            // Update both original and filtered bookings
            bookings = bookings.map(b =>
                b.id == editBookingId ? { ...b, ...bookingData } : b
            );
            filteredBookings = filteredBookings.map(b =>
                b.id == editBookingId ? { ...b, ...bookingData } : b
            );

            editBookingId = null;
            await loadAndRenderBookings();
        } else {
            const newBooking = await createBooking(bookingData);
            bookings.push(newBooking);
            filteredBookings.push(newBooking);
        }
        renderBookings(filteredBookings, tableBody);
        resetBookingForm(form);

        // Reapply search filter if there's a current search term
        const searchTerm = searchInput.value.toLowerCase().trim();
        if (searchTerm) {
            filteredBookings = bookings.filter(booking => {
                const customerName = booking.customer ? booking.customer.name.toLowerCase() : '';
                const activityName = booking.activityTimeslots &&
                booking.activityTimeslots[0] &&
                booking.activityTimeslots[0].activity
                    ? booking.activityTimeslots[0].activity.activityName.toLowerCase()
                    : '';

                const searchFields = [
                    booking.id.toString().toLowerCase(),
                    customerName,
                    booking.date.toLowerCase(),
                    booking.minAge.toString().toLowerCase(),
                    booking.price.toString().toLowerCase(),
                    activityName
                ];

                return searchFields.some(field => field.includes(searchTerm));
            });
            renderBookings(filteredBookings, tableBody);
        }
    } catch (err) {
        console.error("Kunne ikke slette/oprette booking", err);
    }
});

async function populateCustomersDropdown() {
    try {
        const customers = await getCustomers();
        customerSelect.innerHTML = `<option value="">Vælg kunde</option>`;
        customers.forEach(customer => {
            const option = document.createElement("option");
            option.value = customer.id;
            option.textContent = customer.name;
            customerSelect.appendChild(option);
        });
    } catch (err) {
        console.error("Fejl ved indlæsning af kunder", err);
    }
}