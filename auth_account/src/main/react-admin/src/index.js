import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';

import './devhelper/login'; // 调试时自动登录
import post from './helper/ApiHelper';

post("/api/user/get_user_info")
.then(data => {
    if (data.error !== 0)
        return console.error(data.msg);
    window.user = data.user;
    window.auth = data.auth;
})
.catch(() => {
    window.user = {};
});

ReactDOM.render(<App />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
