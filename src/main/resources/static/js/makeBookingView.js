export function renderTimeslots(timeslots, containerEl, activities) {
    containerEl.innerHTML = "";

    timeslots.forEach((slot) => {
        const div = document.createElement("div");
        div.classList.add("timeslot");

        const info = document.createElement("div");
        info.innerText = `${formatTime(slot.startTime)} - ${formatTime(slot.endTime)}`;

        const activitySelect = document.createElement("select");
        activitySelect.innerHTML = `<option value="">Vælg aktivitet </option>` +
            activities.map(a => `<option value="${a.id}">${a.activityName}</option>`).join("");

        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.classList.add("slot-select");

        div.appendChild(checkbox);
        div.appendChild(info);
        div.appendChild(activitySelect);

        // Store metadata for later use
        div.dataset.id = slot.id;
        div.dataset.startTime = slot.startTime;
        div.dataset.endTime = slot.endTime;

        containerEl.appendChild(div);
    });
}

export function collectFormData(dateStr) {
    const selectedBlocks = Array.from(document.querySelectorAll(".timeslot"))
        .filter(ts => ts.querySelector(".slot-select").checked)
        .map(ts => ({
            id: Number(ts.dataset.id),
            startTime: ts.dataset.startTime,
            endTime: ts.dataset.endTime,
            activityId: Number(ts.querySelector("select").value)
        }));

    return {
        name: document.getElementById("customer-name").value,
        email: document.getElementById("customer-email").value,
        phone: document.getElementById("customer-phone").value,
        minAge: document.getElementById("min-age").value,
        selectedTimeslots: selectedBlocks
    };
}

function formatTime(dateTimeStr) {
    const date = new Date(dateTimeStr);
    const h = String(date.getHours()).padStart(2, "0");
    const m = String(date.getMinutes()).padStart(2, "0");
    return `${h}:${m}`;
}