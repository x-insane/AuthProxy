import React from "react";
import "../lib/gt.js";

import post from "../helper/ApiHelper";

class Geetest extends React.Component {
    name = Math.random().toString(36).substr(2);
    state = {
        text: true,
        wait: true
    };

    reset = () => {
        if (this.captchaObj)
            this.captchaObj.reset()
    };

    componentDidMount() {
        const that = this;
        post("/geetest/start_captcha?t=" + (new Date()).getTime())
        .then(data => {
            that.setState({
                text: false
            });
            window.initGeetest({
                // 以下 4 个配置参数为必须，不能缺少
                gt: data.gt,
                challenge: data.challenge,
                offline: !data.success, // 表示用户后台检测极验服务器是否宕机
                new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机

                product: "float", // 产品形式，包括：float，popup
                width: window.innerWidth > 575 ? "260px" : ((window.innerWidth - 62) + "px")

                // 更多前端配置参数说明请参见：http://docs.geetest.com/install/client/web-front/
            }, captchaObj => {
                captchaObj.appendTo('#' + that.name + '_captcha');
                captchaObj.onReady(() => {
                    that.setState({
                        wait: false
                    });
                });
                captchaObj.onSuccess(() => {
                    if (this.props.onSuccess)
                        this.props.onSuccess()
                });
                that.captchaObj = captchaObj
            })
        })
    }

    render() {
        return <div className="form-item">
            <div id={this.name + "_captcha"}>
                {
                    this.state.text ? <div id={this.name + "_text"}>
                        行为验证™ 安全组件加载中
                    </div> : null
                }
                {
                    this.state.wait ? <div id={this.name + "_wait"} className="show">
                        <div className="loading">
                            <div className="loading-dot"/>
                            <div className="loading-dot"/>
                            <div className="loading-dot"/>
                            <div className="loading-dot"/>
                        </div>
                    </div> : null
                }
            </div>
        </div>
    }
}

export default Geetest;