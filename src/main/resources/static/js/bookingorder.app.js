import {
    getAllBookingOrders,
    createBookingOrder,
    updateBookingOrder,
    deleteBookingOrder,
} from "./api/bookingOrderApi.js";

const tbody = document.querySelector("#activity-table-body");
const form = document.querySelector("#booking-form");
const idInput = document.querySelector("#booking-id");
const nameInput = document.querySelector("#booking-name");
const descriptionInput = document.querySelector("#booking-description");
const priceInput = document.querySelector("#booking-price");

// Hent og vis bookinger
async function loadBookings() {
    tbody.innerHTML = "";
    try {
        const bookings = await getAllBookingOrders();
        bookings.forEach(renderBookingRow);
    } catch (err) {
        console.error(err);
    }
}

// Render én række i tabellen
function renderBookingRow(booking) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
    <td>${booking.id}</td>
    <td>${booking.name}</td>
    <td>${booking.description}</td>
    <td>${booking.price}</td>
    <td>
      <button data-action="edit" data-id="${booking.id}">✏️</button>
      <button data-action="delete" data-id="${booking.id}">🗑️</button>
    </td>
  `;
    tbody.appendChild(tr);
}

// Gem booking (opret eller rediger)
form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = idInput.value;
    const booking = {
        name: nameInput.value,
        description: descriptionInput.value,
        price: parseFloat(priceInput.value),
    };

    try {
        if (id) {
            await updateBookingOrder(id, booking);
            alert("Booking opdateret!");
        } else {
            await createBookingOrder(booking);
            alert("Booking oprettet!");
        }
        form.reset();
        idInput.value = "";
        await loadBookings();
    } catch (err) {
        console.error(err);
        alert("Fejl ved gemning!");
    }
});

// Håndter klik på rediger/slet
tbody.addEventListener("click", async (e) => {
    const btn = e.target.closest("button");
    if (!btn) return;

    const id = btn.dataset.id;
    const action = btn.dataset.action;

    if (action === "delete") {
        if (confirm("Er du sikker på, at du vil slette denne booking?")) {
            await deleteBookingOrder(id);
            await loadBookings();
        }
    }

    if (action === "edit") {
        // Find booking-data direkte fra rækken
        const tr = btn.closest("tr");
        idInput.value = id;
        nameInput.value = tr.children[1].textContent;
        descriptionInput.value = tr.children[2].textContent;
        priceInput.value = tr.children[3].textContent;
    }
});

// Hent bookinger når siden loader
loadBookings();