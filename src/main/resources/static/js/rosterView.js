import { getEmployees } from "./api/employeeApi.js";
import {
    getRosterByDate,
    createRoster,
    updateRoster,
    createRosterForEmployee,
    getRosters,
} from "./api/rosterApi.js";

console.log("✅ rosterView.js loaded");

const rosterDateInput = document.getElementById("roster-date");
const rosterTable = document.getElementById("roster-table");

// Default date = today
rosterDateInput.value = new Date().toISOString().split("T")[0];

// === Load the roster and render the UI ===
async function loadRoster(date) {
    try {
        const [employees, roster] = await Promise.all([
            getEmployees(),
            getRosterByDate(date),
        ]);

        rosterTable.innerHTML = "";

        // Build one editable row per employee
        employees.forEach((emp) => {
            const existing = roster.find((r) => r.employee?.id === emp.id);
            const shiftStart = existing?.shiftStart?.slice(0, 5) || "";
            const shiftEnd = existing?.shiftEnd?.slice(0, 5) || "";

            const div = document.createElement("div");
            div.classList.add("roster-row");
            div.innerHTML = `
        <span class="name">${emp.userName}</span>
        <input type="time" class="shift-start" value="${shiftStart}">
        <input type="time" class="shift-end" value="${shiftEnd}">
        <button class="save-btn">💾</button>
      `;

            // Save button click handler
            div.querySelector(".save-btn").addEventListener("click", async () => {
                const start = div.querySelector(".shift-start").value;
                const end = div.querySelector(".shift-end").value;

                if (!start || !end) {
                    alert("Please select both start and end times before saving.");
                    return;
                }

                // If roster entry already exists → update, else create
                if (existing) {
                    const updatedRoster = {
                        ...existing,
                        shiftStart: start,
                        shiftEnd: end,
                        employee: { id: emp.id },
                        date,
                    };
                    await updateRoster(existing.id, updatedRoster);
                } else {
                    // Option A: Use the simple POST /api/roster body method
                    const newRoster = {
                        date,
                        shiftStart: start,
                        shiftEnd: end,
                        employee: { id: emp.id },
                    };
                    await createRoster(newRoster);

                    // Option B: (if you want the /employee/{id}?date=... route instead)
                    // await createRosterForEmployee(emp.id, date, start, end);
                }

                alert(`✅ Saved shift for ${emp.userName}`);
                await loadRoster(date); // Refresh after save
            });

            rosterTable.appendChild(div);
        });
    } catch (err) {
        console.error("Failed to load roster:", err);
    }
}

// === Handle date change ===
rosterDateInput.addEventListener("change", (e) => {
    loadRoster(e.target.value);
});

// === Initial load ===
loadRoster(rosterDateInput.value);
