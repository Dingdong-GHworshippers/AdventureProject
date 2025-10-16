import { getActivity, updateActivity, deleteActivity, createActivity } from "./api/activityApi.js";

window.addEventListener("DOMContentLoaded", initApp);

function initApp() {
    reloadAndRender();
    setupEventListeners();
}

function setupEventListeners() {
    const tableBody = document.querySelector("#activity-table-body");
    const form = document.querySelector("#activity-form");

    tableBody.addEventListener("click", handleTableClick);
    form.addEventListener("submit", handleFormSubmit);
}

/* ------------------- EVENT HANDLERS ------------------- */

async function handleTableClick(event) {
    const target = event.target;

    const row = target.closest("tr");
    if (!row) return;
    const activityId = row.getAttribute("data-id");

    // Delete
    if (target.classList.contains("delete-button")) {
        await deleteActivity(activityId);
        await reloadAndRender();
        return;
    }

    // Edit
    if (target.classList.contains("edit-button")) {
        const activityToEdit = {
            id: activityId,
            activityName: row.children[1].textContent,
            activityDescription: row.children[2].textContent,
            minAge: parseInt(row.children[3].textContent),
            activityPrice: parseFloat(row.children[4].textContent),
        };
        fillActivityForm(activityToEdit);
        return;
    }

    // BOOK
    if (target.classList.contains("book-button")) {
        window.location.href = `/make-booking-page.html?activityId=${activityId}`;
    }
}

async function handleFormSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    const activity = {
        id: formData.get("id"),
        activityName: formData.get("activity-name"),
        activityDescription: formData.get("activity-description"),
        minAge: parseInt(formData.get("minAge")),
        activityPrice: parseFloat(formData.get("activity-price")),
    };

    try {
        if (activity.id) {
            await updateActivity(activity.id, activity);
        } else {
            await createActivity(activity);
        }
        form.reset();
        form.querySelector("#id").value = "";
        await reloadAndRender();
    } catch (err) {
        console.error("Error saving activity:", err);
    }
}

/* ------------------- HELPER FUNCTIONS ------------------- */

function fillActivityForm(activity) {
    document.querySelector("#id").value = activity.id;
    document.querySelector("#activity-name").value = activity.activityName;
    document.querySelector("#activity-description").value = activity.activityDescription;
    document.querySelector("#minAge").value = activity.minAge;
    document.querySelector("#activity-price").value = activity.activityPrice;
}

async function reloadAndRender() {
    const activities = await getActivity();
    renderTable(activities);
}

function renderTable(activities) {
    const tableBody = document.querySelector("#activity-table-body");
    tableBody.innerHTML = "";
    activities.forEach(renderRow);
}

function renderRow(activity) {
    const tableBody = document.querySelector("#activity-table-body");
    const html = /*html*/ `
        <tr data-id="${activity.id}" class="activity-container">
            <td>${activity.id}</td>
            <td>${activity.activityName}</td>
            <td>${activity.activityDescription}</td>
            <td>${activity.minAge}</td>
            <td>${activity.activityPrice}</td>
            <td>
                <button class="edit-button" data-id="${activity.id}">Edit</button>
                <button class="delete-button" data-id="${activity.id}">Delete</button>
                <button type="button" class="book-button" data-id="${activity.id}">Book tid</button>
            </td>
        </tr>
    `;
    tableBody.insertAdjacentHTML("beforeend", html);
}
