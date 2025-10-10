import { get, del, put, post } from "../util/fetch.js";

const TIMESLOT_URL = "http://localhost:8080/api/timeslot";

export async function getTimeslots() {
    return get(TIMESLOT_URL);
}

export async function createTimeslot(activity) {
    return post(TIMESLOT_URL, activity);
}

export async function updateTimeslot(id, newActivity) {
    return put(`${TIMESLOT_URL}/${id}`, newActivity);
}

export async function deleteTimeslot(id) {
    return del(`${TIMESLOT_URL}/${id}`);
}

export async function getTimeslotsByDate(isoDate) {
    return get(`${TIMESLOT_URL}/by-date?date=${encodeURIComponent(isoDate)}`);
}

export async function assignEmployee(timeslotId, employeeId) {
    return post(`${TIMESLOT_URL}/${timeslotId}/assign`, employeeId);
}

export async function unassignEmployee(timeslotId, employeeId) {
    return post(`${TIMESLOT_URL}/${timeslotId}/unassign`, employeeId);
}
