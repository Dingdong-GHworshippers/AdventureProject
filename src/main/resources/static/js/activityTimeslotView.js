// === RENDERS TIMESLOTS TO TABLE ===
export function renderActivityTimeslots(timeslots, tableBodyEl) {
    tableBodyEl.innerHTML = "";
    timeslots.forEach(timeslot => {
        const row = document.createElement("tr");
        row.dataset.timeslotId = timeslot.id;

        const employeeList = timeslot.employees?.map(emp => emp.name).join(", ") || "Ingen";

        let startTime = new Date(timeslot.startTime);
        let endTime = new Date(timeslot.endTime);

        let formatStart = formatTime(startTime);
        let formatEnd = formatTime(endTime);

        row.innerHTML = `
            <td>${timeslot.id}</td>
            <td>${formatStart}</td>
            <td>${formatEnd}</td>
            <td>${timeslot.activity?.activityName || "Ukendt aktivitet"}</td>
            <td>${employeeList}</td>
            <td>
                <button class="edit-btn">Rediger</button>
                <button class="delete-btn">Slet</button>
            </td>
        `;

        tableBodyEl.appendChild(row);
    });
}

// === FILLS FORM FOR EDITING TIMESLOT ===
export function fillTimeslotForm(form, timeslot) {
    let startTime = new Date(timeslot.startTime);
    let endTime = new Date(timeslot.endTime);

    form.timeslotId.value = timeslot.id || "";
    form.startTime.value = formatTime(startTime) || "";
    form.endTime.value = formatTime(endTime) || "";
    form.activity.value = timeslot.activity?.id || "";
    form.employee.value = timeslot.employees?.map(e => e.name).join(",") || "";
}

// === RESET FORM FIELDS ===
export function resetTimeslotForm(form) {
    form.reset();
}


function formatTime(date) {
    const h = date.getHours().toString().padStart(2, "0");
    const m = date.getMinutes().toString().padStart(2, "0");
    return `${h}:${m}`;
}