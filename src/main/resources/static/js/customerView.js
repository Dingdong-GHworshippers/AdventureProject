// ==== READS CUSTOMERS AND DISPLAYS THEM ====
export function renderCustomers(customers, tableBodyEl) {
    tableBodyEl.innerHTML = "";
    customers.forEach(customer => {
        const row = document.createElement("tr");
        row.dataset.customerId = customer.id;
        row.innerHTML = `
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td>${customer.phoneNumber}</td>
            <td>
                <button class="edit-btn">Edit</button>
                <button class="delete-btn">Delete</button>
            </td>
        `;
        tableBodyEl.appendChild(row);
    });
}

// ==== FILLS FORM FOR EDITING CUSTOMERS. USED ON EDIT CLICK
export function fillCustomerForm(form, customer) {
    form.name.value = customer.name || "";
    form.email.value = customer.email || "";
    form.phoneNumber.value = customer.phoneNumber || "";
}

export function resetCustomerForm(form) {
    form.reset();
}