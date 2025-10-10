import {get, del, put, post} from "../util/fetch.js";

const BASE_URL = "http://localhost:8080/api";
const TIMESLOT_URL = `${BASE_URL}/timeslot`;

export async function getTimeslots(){
    return get(TIMESLOT_URL);
}

export async function createTimeslot(activity){
    return post(TIMESLOT_URL, activity);
}

export async function updateTimeslot(id, newActivity){
    return put(`${TIMESLOT_URL}/${id}`, newActivity);
}

export async function deleteTimeslot(id){
    console.log(id  + id);

    return del(`${TIMESLOT_URL}/${id}`);
}

export async function getTimeslotsByDate(isoDateTime) {
    return get(`${TIMESLOT_URL}/by-date?date=${encodeURIComponent(isoDateTime)}`);
}