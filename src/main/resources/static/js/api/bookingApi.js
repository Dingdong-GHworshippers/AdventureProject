import {get, del, put, post} from "../util/fetch.js";

const BASE_URL = "http://localhost:8080/api";
const BOOKING_URL = `${BASE_URL}/bookings`;

export async function getBookings(){
    return get(BOOKING_URL);
}

export async function createBooking(activity){
    return post(BOOKING_URL, activity);
}

export async function updateBooking(id, newBooking){
    return put(`${BOOKING_URL}/${id}`, newBooking);
}

export async function deleteBooking(id){
    console.log(id  + id);

    return del(`${BOOKING_URL}/${id}`);
}
