import {getActivity, updateActivity, deleteActivity, createActivity} from "./api/activityApi.js";

console.log("✅ app.js loaded");
window.addEventListener("DOMContentLoaded", initApp);

// === Sort globals ===
let currentSortKey = "id";
let isAscending = true;

function initApp(){
    reloadAndRender();
}

const tableBody = document.querySelector("#activity-table-body");

// Event delegation for Delete and Book buttons
tableBody.addEventListener("click", (e) => {
    const target = e.target;
    const activityId = target.dataset.id;

    if (target.classList.contains("delete-button")) {
        // Call your delete function
        deleteActivity(activityId).then(() => {
            reloadAndRender();
        });
    }

    if (target.classList.contains("book-button")) {
        // Navigate to the booking page for this activity
        window.location.href = `/booking.html?activityId=${activityId}`;
    }
});



async function reloadAndRender() {
    try {
        const activities = await getActivity();
        console.log("Fetched activities:", activities);
        renderTable(activities);
    } catch (e) {
        console.error("Failed to fetch activities:", e);
    }
}

function renderTable(activity){
    const tableBody = document.querySelector("#activity-table-body");
    tableBody.innerHTML = "";
    activity.forEach(renderRow);

}

function renderRow(activity){
    const tableBody = document.querySelector("#activity-table-body")
    const html = /*html*/ `
        <tr data-id="${activity.id}" class="activity-container">
            
            <td>${activity.id}</td>
            <td>${activity.activityName}</td>
            <td>${activity.activityDescription}</td>
            <td>${activity.activityPrice} dkk</td>
            <td>
                <button class="delete-button" data-id="${activity.id}">Delete</button>
            </td>
            <td>
                <button type="submit" class="book-button" data-id="${activity.id}">Book tid</button>
            </td>
        </tr>
    `;
    tableBody.insertAdjacentHTML("beforeend", html);
}
function fillBookEditForm(activity) {
    document.querySelector("#id").value = activity.id;
    document.querySelector("#name").value = activity.activityName;
    document.querySelector("#description").value = activity.activityDescription;
    document.querySelector("#price").value = activity.activityPrice;
}