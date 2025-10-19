const API_URL = "http://localhost:8080/api/products";
const tbody = document.querySelector("#product-table-body");
const form = document.querySelector("#product-form");
const idInput = document.querySelector("#product-id");
const nameInput = document.querySelector("#product-name");
const typeInput = document.querySelector("#product-type");
const priceInput = document.querySelector("#product-price");
const resetBtn = document.querySelector("#reset-btn");

function translateType(type) {
    if (!type) return "ukendt";
    switch (type.toUpperCase()) {
        case "FOOD":
            return "mad";
        case "DRINK":
            return "drikkevare";
        case "MERCH":
            return "merchandise";
        default:
            return type.toLowerCase();
    }
}

// Hent og vis produkter
async function loadProducts() {
    tbody.innerHTML = "";
    try {
        const res = await fetch(API_URL);
        if (!res.ok) throw new Error("Fejl ved hentning af produkter");
        const products = await res.json();

        if (products.length === 0) {
            tbody.innerHTML = `<tr><td colspan="5"><i>Ingen produkter oprettet endnu.</i></td></tr>`;
            return;
        }

        products.forEach(product => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
        <td>${product.id}</td>
        <td>${product.name}</td>
        <td>${translateType(product.type)}</td>
        <td>${product.price.toFixed(2)}</td>
        <td>
          <button class="btn-primary" data-action="edit" data-id="${product.id}">✏️ Rediger</button>
          <button class="btn-danger" data-action="delete" data-id="${product.id}">🗑️ Slet</button>
        </td>
      `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        console.error("Fejl ved hentning af produkter:", err);
    }
}

// Gem produkt (opret/ret)
form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const product = {
        name: nameInput.value.trim(),
        type: typeInput.value,
        price: parseFloat(priceInput.value)
    };

    try {
        if (idInput.value) {
            // Opdater produkt
            await fetch(`${API_URL}/${idInput.value}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(product)
            });
            alert("✅ Produkt opdateret!");
        } else {
            // Opret nyt produkt
            await fetch(API_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(product)
            });
            alert("✅ Produkt oprettet!");
        }

        form.reset();
        idInput.value = "";
        await loadProducts();
    } catch (err) {
        console.error("Fejl ved gemning:", err);
    }
});

// Klik på rediger/slet
tbody.addEventListener("click", async (e) => {
    const btn = e.target.closest("button");
    if (!btn) return;

    const id = btn.dataset.id;
    const action = btn.dataset.action;

    if (action === "edit") {
        const res = await fetch(`${API_URL}/${id}`);
        const product = await res.json();

        idInput.value = product.id;
        nameInput.value = product.name;
        typeInput.value = product.type;
        priceInput.value = product.price;
    }

    if (action === "delete") {
        if (confirm("Er du sikker på, at du vil slette dette produkt?")) {
            await fetch(`${API_URL}/${id}`, { method: "DELETE" });
            await loadProducts();
        }
    }
});

// Nulstil formular
resetBtn.addEventListener("click", () => {
    form.reset();
    idInput.value = "";
});

loadProducts();