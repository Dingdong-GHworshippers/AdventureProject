import {get, del, put, post} from "../util/fetch.js";

const BASE_URL = "http://localhost:8080/api";
const CUSTOMER_URL = `${BASE_URL}/customers`;

export async function getCustomers(){
    return get(CUSTOMER_URL);
}

export async function createCustomer(customer){
    return post(CUSTOMER_URL, customer);
}

export async function updateCustomer(id, newCustomer){

    return put(`${CUSTOMER_URL}/${id}`, newCustomer);
}

export async function deleteCustomer(id){
    console.log(id  + id);

    return del(`${CUSTOMER_URL}/${id}`);
}
