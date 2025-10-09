import {
    getAllBookingOrders,
    deleteBookingOrder
} from "./api/bookingOrderApi.js";

const API_URL = "http://localhost:8080/api/booking-orders";
const tbody = document.querySelector("#orders-body");

// Hent og vis alle ordrer
async function loadOrders() {
    tbody.innerHTML = "";
    try {
        const orders = await getAllBookingOrders();
        orders.forEach(renderOrderRow);
    } catch (err) {
        console.error("Fejl ved hentning af ordrer:", err);
    }
}

// Byg én række i tabellen
function renderOrderRow(order) {
    const tr = document.createElement("tr");

    // Hent aktivitetsdata, hvis de findes
    const activityName = order.booking?.activity?.name || "<i>Ingen aktivitet</i>";
    const bookingId = order.booking?.id ? `#${order.booking.id}` : "–";

    const productList = order.products
        .map(p => `${p.name} (kr. ${p.price})`)
        .join("<br>");

    tr.innerHTML = `
        <td>${order.id}</td>
        <td>${activityName} ${bookingId}</td>
        <td>${order.total.toFixed(2)} kr.</td>
        <td>${productList || "<i>Ingen produkter</i>"}</td>
        <td>
            <input type="number" min="1" placeholder="Produkt-ID" style="width:90px">
            <button data-action="add-product" data-id="${order.id}">➕</button>
        </td>
        <td>
            <button data-action="delete-order" data-id="${order.id}">🗑️ Slet</button>
        </td>
    `;

    // Tilføj evt. knapper til at fjerne produkter direkte:
    if (order.products && order.products.length > 0) {
        const removeButtons = order.products.map(p =>
            `<button data-action="remove-product" data-order="${order.id}" data-product="${p.id}">❌ ${p.name}</button>`
        ).join(" ");
        const removeTd = document.createElement("td");
        removeTd.innerHTML = removeButtons;
        tr.appendChild(removeTd);
    }

    tbody.appendChild(tr);
}

// Håndter klik i tabellen
tbody.addEventListener("click", async (e) => {
    const btn = e.target.closest("button");
    if (!btn) return;

    const action = btn.dataset.action;
    const orderId = btn.dataset.id || btn.dataset.order;

    if (action === "delete-order") {
        if (confirm("Er du sikker på, at du vil slette denne ordre?")) {
            try {
                await deleteBookingOrder(orderId);
                await loadOrders();
            } catch (err) {
                console.error("Fejl ved sletning:", err);
            }
        }
    }

    if (action === "add-product") {
        const input = btn.previousElementSibling;
        const productId = input.value.trim();
        if (!productId) return alert("Indtast et produkt-ID først.");

        try {
            await fetch(`${API_URL}/${orderId}/products/${productId}`, { method: "POST" });
            await loadOrders();
        } catch (err) {
            console.error("Fejl ved tilføjelse af produkt:", err);
        }
    }

    if (action === "remove-product") {
        const productId = btn.dataset.product;
        try {
            await fetch(`${API_URL}/${orderId}/products/${productId}`, { method: "DELETE" });
            await loadOrders();
        } catch (err) {
            console.error("Fejl ved fjernelse af produkt:", err);
        }
    }
});

// Hent ordrer ved load
loadOrders();