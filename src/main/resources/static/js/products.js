const API_URL = "http://localhost:8080/api/products";
const productList = document.querySelector("#product-list");

// 🔹 Funktion til at oversætte enum til dansk tekst
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

// 🔹 Hent og vis produkter
async function loadProducts() {
    try {
        const res = await fetch(API_URL);
        if (!res.ok) throw new Error("Fejl ved hentning af produkter");
        const products = await res.json();

        if (products.length === 0) {
            productList.innerHTML = "<p>Der er ingen produkter tilgængelige lige nu.</p>";
            return;
        }

        // 🔹 Generér produktkort dynamisk
        productList.innerHTML = products.map(p => `
      <div class="product-card">
       
        <h3>${p.name}</h3>
        <h4>${p.price} kr.</h4>
        <p>Type: ${translateType(p.type)}</p>
      </div>
    `).join("");
    } catch (err) {
        console.error(err);
        productList.innerHTML = "<p>Kunne ikke indlæse produkter.</p>";
    }
}

// 🔹 Indlæs produkter, når siden åbnes
loadProducts();