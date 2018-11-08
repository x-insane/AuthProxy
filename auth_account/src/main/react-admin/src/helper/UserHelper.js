import post from "./ApiHelper";

let user, auth, pending;
let callbacks = [];

const getUser = callback => {
    if (pending)
        return callbacks.push(callback);
    pending = true;
    post("/api/user/get_user_info")
    .then(data => {
        if (data.error !== 0)
            return console.error(data.msg);
        user = data.user;
        auth = data.auth;
    })
    .catch(e => {
        user = {};
        console.error(e)
    })
    .finally(() => {
        callback();
        callbacks.forEach(callback => callback());
        callbacks = [];
        pending = false
    })
};

class UserHelper {
    static getUserInfo = callback => {
        if (user)
            return callback(user);
        getUser(() => {
            callback(user)
        })
    };

    static setUserInfo = u => {
        user = u
    };

    static getUserAuth = callback => {
        if (auth)
            return auth;
        getUser(() => {
            callback(auth)
        })
    };
}

export default UserHelper;