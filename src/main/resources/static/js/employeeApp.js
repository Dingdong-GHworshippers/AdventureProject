import {getEmployees, deleteEmployee, createEmployee} from "./api/employeeApi.js";
import { sortBy } from "./util/sortUtil.js";

console.log("✅ app.js loaded");
window.addEventListener("DOMContentLoaded", initApp);

// === Sort globals ===
let currentSortKey = "id";
let isAscending = true;

function initApp(){
    reloadAndRender();
    setupEventListeners()
}

function setupEventListeners() {
    const tableBody = document.querySelector("#employee-table-body");
    tableBody.addEventListener("click", handleTableClick);
    const form = document.querySelector("#employee-form");
    form.addEventListener("submit", handleFormSubmit);
    document.querySelector("thead").addEventListener("click", handleSortClick);
}

async function reloadAndRender() {
    try {
        const employees = await getEmployees();
        console.log("Fetched activities:", employees);
        const sortedEmployees = employees.sort(sortBy(currentSortKey, isAscending))
        renderTable(sortedEmployees);
        updateSortIndicator();
    } catch (e) {
        console.error("Failed to fetch employees:", e);
    }
}

function updateSortIndicator() {
    const thElems = document.querySelectorAll("th[data-key]");
    thElems.forEach(th => {
        th.classList.remove("sorted-asc", "sorted-desc");
        if (th.getAttribute("data-key") === currentSortKey) {
            th.classList.add(isAscending ? "sorted-asc" : "sorted-desc");
        }
    });
}



//--- EVENT ----//
async function handleFormSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    const employee = {
        userName: formData.get("username"),
        password: formData.get("password"),
        role: formData.get("role"),
    };

    await createEmployee(employee);
    form.reset();
    await reloadAndRender();
}


async function handleTableClick(event) {
    const target = event.target;
    if (target.classList.contains("delete-button")) {
        const row = target.closest("tr");
        const employeeId = row.getAttribute("data-id");
        try {
            await deleteEmployee(employeeId);
        } catch (e) {
            const message = e.message || "Failed to delete employee.";
            alert(message);
        }
        await reloadAndRender();
    }
}
async function handleSortClick(event) {
    const th = event.target.closest("th");
    if (th === null || th === undefined) {

        return;
    }

    const key = th.getAttribute("data-key");

    if (!key) {
        return;
    }

    if (key === currentSortKey) {
        isAscending = !isAscending;
    } else {
        currentSortKey = key;
        isAscending = true;
    }
    await reloadAndRender();
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
