import { getCustomers, createCustomer, updateCustomer, deleteCustomer } from "./api/customerApi.js";
import { renderCustomers, fillCustomerForm, resetCustomerForm } from "./customerView.js";

const tableBody = document.querySelector("#customers-table tbody");
const form = document.getElementById("customer-form");

let customers = [];
let editCustomerId = null;

document.addEventListener("DOMContentLoaded", async () => {
    await loadAndRenderCustomers();
});

// ==== FETCHES CUSTOMERS
async function loadAndRenderCustomers() {
    try {
        customers = await getCustomers();
        renderCustomers(customers, tableBody);
    } catch (err) {
        console.error("Fejl ved indlæsning af kunder");
    }
}

// == EVENT DELEGATION FOR EDIT AND DELETE. USES FILLCUSTOMERFORM FROM CUSTOMERVIEW TO EDIT IF THERE IS ID PRESENT
tableBody.addEventListener("click", async (event) => {
    const row = event.target.closest("tr");
    if (!row) return;

    const customerId = row.dataset.customerId;
    const customer = customers.find(c => c.id == customerId);

    if (event.target.classList.contains("edit-btn")) {
        if (!customer) return;
        fillCustomerForm(form, customer);
        editCustomerId = customer.id;
        console.log(`Redigerer kunde: ${customer.name}`);
    }

    if (event.target.classList.contains("delete-btn")) {
        try {
            await deleteCustomer(customerId);
            customers = customers.filter(c => c.id != customerId);
            renderCustomers(customers, tableBody);
            console.log("Kunde slettet");
        } catch (err) {
            console.error("Fejl ved sletning");
        }
    }
});

// ===== SENDS JSON TO BACKEND WHEN CLICKING SUBMIT
form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const customerData = {
        name: form.name.value,
        email: form.email.value,
        phoneNumber: form.phoneNumber.value,
        // bookings: []
    };

    try {
        if (editCustomerId) {
            await updateCustomer(editCustomerId, customerData);
            customers = customers.map(c => c.id == editCustomerId ? { ...c, ...customerData } : c);
            console.log("Kunde opdateret");
            editCustomerId = null;
        } else {
            const newCustomer = await createCustomer(customerData);
            customers.push(newCustomer);
            console.log("Kunde oprettet");
        }
        renderCustomers(customers, tableBody);
        resetCustomerForm(form);
    } catch (err) {
        console.error("Fejl ved oprettelse eller opdatering");
    }
});