import {getActivity, updateActivity, deleteActivity, createActivity} from "./api/activityApi.js";

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
        <tr data-id="${activity.id}">
            <td>${activity.id}</td>
            <td>${activity.activityName}</td>
            <td>${activity.activityDescription}</td>
            <td>${activity.activityPrice}</td>
            <td>
                <button class="delete-button">Delete</button>
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