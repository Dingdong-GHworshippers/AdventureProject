const form = document.querySelector("#login-form");
const errorP = document.querySelector("#error");

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.querySelector("#username").value.trim();
    const password = document.querySelector("#password").value.trim();

    try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            throw new Error("Forkert brugernavn eller adgangskode");
        }

        const user = await response.json();

        // Gem brugeren i sessionStorage
        sessionStorage.setItem("loggedInUser", JSON.stringify(user));

        // Gå videre til admin-dashboard
        window.location.href = "/admin-page.html";
    } catch (err) {
        errorP.textContent = err.message;

    }
});