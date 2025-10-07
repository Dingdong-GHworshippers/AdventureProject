import {get, del, put, post} from "../utils/fetch.js";

const BASE_URL = "http://localhost:8080/api";
const ACTIVITY_URL = `${BASE_URL}/activities`;

export async function getActivity(){
    return get(ACTIVITY_URL);
}

export async function createActivity(activity){
    return post(ACTIVITY_URL, activity);
}

export async function updateActivity(id, newActivity){
    return put(`${BASE_URL}/${id}`, newActivity);
}

export async function deleteActivity(id){
    console.log("id " + id);

    return del(`${BASE_URL}/${id}`);
}
