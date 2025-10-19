const API_URL = "http://localhost:8080/api/products";

export async function getAllProducts() {
    const res = await fetch(API_URL);
    return res.json();
}

export async function createProduct(product) {
    const res = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product),
    });
    return res.json();
}

export async function updateProduct(id, product) {
    const res = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product),
    });
    return res.json();
}

export async function deleteProduct(id) {
    await fetch(`${API_URL}/${id}`, { method: "DELETE" });
}