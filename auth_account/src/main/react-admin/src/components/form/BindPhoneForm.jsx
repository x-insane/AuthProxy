import React from "react";
import {Button, message, Spin} from "antd";
import post from "../../helper/ApiHelper";
import Geetest from "../Geetest";
import FormItem from "./item/FormItem";
import FormSubmitItem from "./item/FormSubmitItem";
import UserHelper from "../../helper/UserHelper";

class BindPhoneForm extends React.Component {
    state = {
        user: null,
        sending: false, // 发送短信状态，false 未发送，true 正在请求，number 重发等待时间（已请求成功）
        binding: false, // 是否成功发送过短信，成功发送后提交按钮才可用，此时不可修改手机号
        pending: false // 提交状态
    };

    getFields = () => {
        const inputs = this.refs.form.querySelectorAll("input[name]");
        let data = {};
        for (let i=0; i<inputs.length; ++i)
            data[inputs[i].name] = inputs[i].value;
        return data
    };

    sendMessage = () => {
        if (!this.refs.item_phone.validate() ||
            !this.refs.item_geetest.validate())
            return;
        const data = this.getFields();
        this.setState({
            sending: true
        });
        post("/api/user/bind_phone_send_message", data)
        .then(res => {
            if (res.error === 0) {
                let sending = 30;
                const that = this;
                that.setState({
                    sending: sending,
                    binding: true
                });
                setTimeout(function loop() {
                    if (that.state.sending === false)
                        return;
                    if (--sending === 0) {
                        that.setState({
                            sending: false
                        });
                    } else {
                        that.setState({
                            sending: sending
                        });
                        setTimeout(loop, 1000);
                    }
                }, 1000)
            } else message.error(res.msg)
        })
        .catch(() => {
            message.error("网络错误!")
        })
        .finally(() => {
            if (this.state.sending === true) {
                this.setState({
                    sending: false
                })
            }
        })
    };

    handleSubmit = (e) => {
        e.preventDefault();
        if (!this.refs.item_phone.validate() ||
            !this.refs.item_geetest.validate() ||
            !this.refs.item_code.validate())
            return;
        const data = this.getFields();
        this.setState({
            pending: true
        });
        post("/api/user/bind_phone_by_code", data)
        .then(res => {
            if (res.error === 0) {
                message.success("绑定成功");
                const user = this.state.user;
                user.phone = data.phone;
                this.setState({
                    user: user
                });
                UserHelper.setUserInfo(user)
            } else {
                message.error(res.msg);
                this.refs.geetest.reset()
            }
            if (res.error === 201) {
                this.setState({
                    sending: false,
                    binding: false
                })
            }
        })
        .catch(() => {
            message.error("网络错误!");
            this.refs.geetest.reset()
        })
        .finally(() => {
            this.setState({
                pending: false
            });
        })
    };

    resetPhone = e => {
        e.preventDefault();
        this.setState({
            binding: false
        })
    };

    componentDidMount = () => {
        UserHelper.getUserInfo(user => {
            if (user) {
                this.setState({
                    user: user
                })
            }
        })
    };

    render() {
        return !this.state.user ?

        <div style={{textAlign: "center"}}>
            <Spin />
        </div> : (this.state.user.phone ?

        <div>您已绑定手机号：{this.state.user.phone}</div> :

        <form ref="form" className="ant-form ant-form-horizontal" onSubmit={ this.handleSubmit }>

            <FormItem ref="item_phone"
                      label="手机号"
                      name="phone"
                      disabled={this.state.binding}
                      required
                      pattern={/^1[2-9][0-9]{9}$/} />

            <FormItem ref="item_geetest" label="人机验证" noinput required rule={(value, error) => {
                    if (!this.refs.geetest.captchaObj || !this.refs.geetest.captchaObj.getValidate()) {
                        error("请先完成人机验证");
                        return false
                    }
                    return true
                }
            } >
                <Geetest ref="geetest" onSuccess={() => {
                    this.refs.item_geetest.validate()
                }}/>
            </FormItem>

            <FormItem ref="item_code" name="code" label="手机验证码" required addon={
                <Button onClick={this.sendMessage}
                        type="primary"
                        block={true}
                        htmlType="button"
                        disabled={this.state.sending > 0}
                        loading={ this.state.sending === true }>
                    获取验证码{ this.state.sending !== true && this.state.sending > 0 ? "(" + this.state.sending + ")" : "" }
                </Button>
            }/>

            <FormSubmitItem>
                <Button type="primary" htmlType="submit" loading={this.state.pending} disabled={!this.state.binding}>
                    确认绑定
                </Button>
                {
                    this.state.binding && this.state.sending === false ?
                    <Button style={{ marginLeft: "12px" }} htmlType="reset" onClick={ this.resetPhone }>
                        重新填写手机号
                    </Button> : null
                }
            </FormSubmitItem>
        </form>)
    }
}

export default BindPhoneForm;