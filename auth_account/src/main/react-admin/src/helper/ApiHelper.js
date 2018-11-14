const key = document.querySelector("meta[name=_csrf_key]").content;
const token = document.querySelector("meta[name=_csrf_token]").content;

function ApiHelper(url, json) {
    return fetch(url, {
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            '_csrf_key' : key,
            '_csrf_token' : token
        },
        method: "POST",
        body: JSON.stringify(json)
    })
    .then(res => res.json())
}

export default ApiHelper;