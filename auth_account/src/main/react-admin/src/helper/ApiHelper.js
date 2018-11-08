function ApiHelper(url, json) {
    return fetch(url, {
        // headers: {
        //     'Content-Type': 'application/json;charset=UTF-8'
        // },
        method: "POST",
        body: JSON.stringify(json)
    })
    .then(res => res.json())
    .catch(e => {
        console.log(e)
    })
}

export default ApiHelper;