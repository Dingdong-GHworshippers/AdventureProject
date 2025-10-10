function createFetchOptions(httpMethod, body, headers = {}) {
    const options = {
        method: httpMethod,
        headers: {
            "Accept": "application/json",
            ...headers
        }
    };

    if (body) {
        options.body = JSON.stringify(body);
        options.headers["Content-Type"] = "application/json";
    }

    return options;
}

async function handleResponse(res) {
    if (!res.ok) {
        const data = await res.json().catch(() => ({}));
        const errorMsg = data.error || `HTTP error ${res.status}`;
        throw new Error(errorMsg);
    }
    return res.status === 204 ? null : res.json();
}

export async function get(url, headers) {
    const options = createFetchOptions("GET", null, headers);
    const res = await fetch(url, options);
    return handleResponse(res);
}

export async function post(url, body, headers) {
    const options = createFetchOptions("POST", body, headers);
    const res = await fetch(url, options);
    return handleResponse(res);
}

export async function put(url, body, headers) {
    const options = createFetchOptions("PUT", body, headers);
    const res = await fetch(url, options);
    return handleResponse(res);
}

export async function del(url, headers) {
    const options = createFetchOptions("DELETE", headers);
    const res = await fetch(url, options);
    return handleResponse(res);
}