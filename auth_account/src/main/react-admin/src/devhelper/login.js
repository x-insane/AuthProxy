if (process.env.NODE_ENV === "development") {
    var data = new FormData();
    data.append("username", "xinsane");
    data.append("password", "123456");
    fetch('/api/user/login', {
        method: 'POST',
        body: data
    })
    .then(res => res.json())
    .then(res => console.log(res))
    .catch(e => {
        console.log(e)
    })
}