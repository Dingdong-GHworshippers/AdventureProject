import {getEmployees, updateEmployee, deleteEmployee, createEmployee} from "./api/employeeApi.js";

console.log("✅ app.js loaded");
window.addEventListener("DOMContentLoaded", initApp);

// === Sort globals ===
let currentSortKey = "id";
let isAscending = true;

function initApp(){
    reloadAndRender();
}

async function reloadAndRender() {
    try {
        const employees = await getEmployees();
        console.log("Fetched activities:", employees);
        renderTable(employees);
    } catch (e) {
        console.error("Failed to fetch employees:", e);
    }
}

function renderTable(employee){
    const tableBody = document.querySelector("#employee-table-body");
    tableBody.innerHTML = "";
    employee.forEach(renderRow);

}

function renderRow(employee){
    const tableBody = document.querySelector("#employee-table-body")
    const html = /*html*/ `
        <tr data-id="${employee.id}">
            <td>${employee.id}</td>
            <td>${employee.userName}</td>
            <td>${employee.role}</td>
            <td>
                <button class="delete-button">Delete</button>
            </td>
        </tr>
    `;
    tableBody.insertAdjacentHTML("beforeend", html);
}
function fillBookEditForm(employee) {
    document.querySelector("#id").value = employee.id;
    document.querySelector("#name").value = employee.username;
    document.querySelector("#description").value = employee.role;
}