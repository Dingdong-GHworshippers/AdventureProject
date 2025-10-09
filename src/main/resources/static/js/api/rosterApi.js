import { get, post, put, del } from "../util/fetch.js";

const BASE_URL = "http://localhost:8080/api";
const ROSTER_URL = `${BASE_URL}/roster`;

// === GET all rosters ===
export async function getRosters() {
    return get(ROSTER_URL);
}

// === GET roster by date ===
export async function getRosterByDate(date) {
    return get(`${ROSTER_URL}/date/${date}`);
}

// === GET roster by ID ===
export async function getRosterById(id) {
    return get(`${ROSTER_URL}/${id}`);
}

// === CREATE new roster entry ===
// expects object like:
// { date: "2025-10-25", shiftStart: "10:00", shiftEnd: "12:00", employee: { id: 1 } }
export async function createRoster(roster) {
    return post(ROSTER_URL, roster);
}

// === UPDATE roster entry ===
export async function updateRoster(id, roster) {
    return put(`${ROSTER_URL}/${id}`, roster);
}

// === DELETE roster entry ===
export async function deleteRoster(id) {
    return del(`${ROSTER_URL}/${id}`);
}

// === Create roster for specific employee/date/time (controller endpoint) ===
export async function createRosterForEmployee(employeeId, date, start, end) {
    const url = `${ROSTER_URL}/employee/${employeeId}?date=${date}&start=${start}&end=${end}`;
    return post(url);
}
