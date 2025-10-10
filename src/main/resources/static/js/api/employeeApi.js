import {get, del, put, post} from "../util/fetch.js";

const BASE_URL = "http://localhost:8080/api";
const EMPLOYEE_URL = `${BASE_URL}/employees`;

export async function getEmployees(){
    return get(EMPLOYEE_URL);
}

export async function createEmployee(employee){
    return post(EMPLOYEE_URL, employee);
}

export async function deleteEmployee(id){
    console.log(id  + id);

    return del(`${EMPLOYEE_URL}/${id}`);
}
