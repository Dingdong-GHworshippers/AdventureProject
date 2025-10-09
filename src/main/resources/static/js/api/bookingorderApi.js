// js/api/bookingOrderApi.js
const API_URL = "http://localhost:8080/api/booking-orders";

// Hent alle bookinger
export async function getAllBookingOrders() {
    const response = await fetch(API_URL);
    if (!response.ok) throw new Error("Fejl ved hentning af bookinger");
    return await response.json();
}

// Opret booking
export async function createBookingOrder(order) {
    const response = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(order),
    });
    if (!response.ok) throw new Error("Fejl ved oprettelse");
    return await response.json();
}

// Opdater booking
export async function updateBookingOrder(id, order) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(order),
    });
    if (!response.ok) throw new Error("Fejl ved opdatering");
    return await response.json();
}

// Slet booking
export async function deleteBookingOrder(id) {
    const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    if (!response.ok) throw new Error("Fejl ved sletning");
}