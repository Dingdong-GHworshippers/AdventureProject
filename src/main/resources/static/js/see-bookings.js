import {
    getAllBookingOrders,
    deleteBookingOrder
} from "./api/bookingOrderApi.js";

const API_URL = "http://localhost:8080/api/booking-orders";
const tbody = document.querySelector("#orders-body");

// Vi har kun 3 produkter – definér dem her
const products = [
    { id: 1, name: "Burger", price: 15 },
    { id: 2, name: "Sodavand", price: 5 },
    { id: 3, name: "T-Shirt", price: 25 }
];

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

    // Booking ID fra booking-klassen
    const bookingId = order.booking?.id ? `#${order.booking.id}` : "–";

    // Produkter i ordren
    const productList = order.products?.length
        ? order.products.map(p => `${p.name} (kr. ${p.price})`).join("<br>")
        : "<i>Ingen produkter</i>";

    // Beregn total — ud fra de aktuelle produkter
    const total = order.products?.reduce((sum, p) => sum + p.price, 0) || 0;

    // Dropdown til at vælge produkt
    const productOptions = `
        <option value="">Vælg produkt...</option>
        ${products.map(p => `<option value="${p.id}">${p.name} (kr. ${p.price})</option>`).join("")}
    `;

    tr.innerHTML = `
        <td>${order.id}</td>
        <td>${bookingId}</td>
        <td>${total.toFixed(2)} kr.</td>
        <td>${productList}</td>
        <td>
            <select id="product-select-${order.id}" style="width:150px">
                ${productOptions}
            </select>
            <button data-action="add-product" data-id="${order.id}">➕</button>
        </td>
        <td>
            <button data-action="delete-order" data-id="${order.id}">🗑️ Slet</button>
        </td>
    `;

    // Tilføj knapper til at fjerne produkter
    if (order.products && order.products.length > 0) {
        const removeButtons = order.products
            .map(p => `<button data-action="remove-product" data-order="${order.id}" data-product="${p.id}">❌ ${p.name}</button>`)
            .join(" ");
        const removeTd = document.createElement("td");
        removeTd.innerHTML = removeButtons;
        tr.appendChild(removeTd);
    }

    tbody.appendChild(tr);
}

// Håndter klik (tilføj/fjern produkt, slet ordre)
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
        const select = document.querySelector(`#product-select-${orderId}`);
        const productId = select.value;
        if (!productId) return alert("Vælg et produkt først.");

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

// Indlæs ordrer ved load
loadOrders();