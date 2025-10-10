import {
    getTimeslots,
    createTimeslot,
    updateTimeslot,
    deleteTimeslot
} from "./api/activityTimeslotApi.js";

import {
    renderActivityTimeslots,
    fillTimeslotForm,
    resetTimeslotForm } from "./activityTimeslotView.js";

const tableBody = document.querySelector("#activity-timeslot-table tbody");
const form = document.getElementById("activity-timeslot-form");

let timeslots = [];
let editTimeslotId = null;

document.addEventListener("DOMContentLoaded", async () => {
    await loadAndRenderTimeslots();
});

// === FETCH AND RENDER TIMESLOTS ===
async function loadAndRenderTimeslots() {
    try {
        timeslots = await getTimeslots();
        let onlyBookedSlots = timeslots.filter(ts => ts.booked === true);

        // Checks if there is an id parameter in the url
        const urlParams = new URLSearchParams(window.location.search);
        const timeslotId = urlParams.get("id");

        // Filter to only show the searched timeslot
        if (timeslotId) {
            onlyBookedSlots = onlyBookedSlots.filter(ts => ts.id == timeslotId);
        }

        renderActivityTimeslots(onlyBookedSlots, tableBody);
    } catch (err) {
        console.error("Fejl ved indlæsning af timeslots", err);
    }
}

// === HANDLE EDIT AND DELETE BUTTON CLICKS ===
tableBody.addEventListener("click", async (event) => {
    const row = event.target.closest("tr");
    if (!row) return;

    const id = row.dataset.timeslotId;
    const timeslot = timeslots.find(t => t.id == id);

    if (event.target.classList.contains("edit-btn")) {
        if (!timeslot) return;
        fillTimeslotForm(form, timeslot);
        editTimeslotId = id;
        console.log(`Redigerer timeslot #${id}`);
    }

    if (event.target.classList.contains("delete-btn")) {
        try {
            await deleteTimeslot(id);
            timeslots = timeslots.filter(t => t.id != id);
            renderActivityTimeslots(timeslots, tableBody);
            console.log(`Timeslot #${id} slettet`);
        } catch (err) {
            console.error("Fejl ved sletning", err);
        }
    }
});

// === HANDLE FORM SUBMISSION ===
form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const timeslotPayload = {
        startTime: form.startTime.value,
        endTime: form.endTime.value,
        activity: {
            id: form.activityId.value
        },
        employees: form.employee.value
            .split(",")
            .map(id => ({ id: parseInt(id.trim()) }))
            .filter(emp => !isNaN(emp.id)),
    };

    try {
        if (editTimeslotId) {
            await updateTimeslot(editTimeslotId, timeslotPayload);
            console.log(`Timeslot #${editTimeslotId} opdateret`);
            editTimeslotId = null;
        } else {
            const newTimeslot = await createTimeslot(timeslotPayload);
            timeslots.push(newTimeslot);
            console.log("Nyt timeslot oprettet");
        }

        await loadAndRenderTimeslots();
        resetTimeslotForm(form);
    } catch (err) {
        console.error("Fejl ved oprettelse eller opdatering", err);
    }
});