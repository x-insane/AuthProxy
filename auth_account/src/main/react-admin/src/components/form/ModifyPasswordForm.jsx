import React from "react";
import { Button, message } from "antd";
import post from "../../helper/ApiHelper";
import FormSubmitItem from "./item/FormSubmitItem";
import FormItem from "./item/FormItem";

class ModifyPasswordForm extends React.Component {
    state = {
        pending: false
    };

    getFields = () => {
        const inputs = this.refs.form.querySelectorAll("input[name]");
        let data = {};
        for (let i=0; i<inputs.length; ++i)
            data[inputs[i].name] = inputs[i].value;
        return data
    };

    validate = () => {
        return this.refs.old_password.validate() &&
               this.refs.new_password.validate() &&
               this.refs.repeat_new_password.validate()
    };

    reset = () => {
        this.refs.old_password.value("");
        this.refs.new_password.value("");
        this.refs.repeat_new_password.value("");
    };

    handleSubmit = (e) => {
        e.preventDefault();
        if (!this.validate())
            return;
        this.setState({
            pending: true
        });
        post("/api/user/modify_password", this.getFields())
        .then(res => {
            if (res.error === 0) {
                message.success("修改成功");
                this.reset()
            }
            else
                message.error(res.msg)
        })
        .catch(() => {
            message.error("网络开了个小差，请稍后再试~")
        })
        .finally(() => {
            this.setState({
                pending: false
            })
        })
    };

    render() {
        return <form ref="form" className="ant-form ant-form-horizontal" onSubmit={ this.handleSubmit }>

            <FormItem ref="old_password" label="旧密码" type="password" name="old_password" required />
            <FormItem ref="new_password" label="新密码" type="password" name="new_password" required
                      rule={(value, error, validate) => {
                          if (!validate())
                              return false;
                          if (this.refs.old_password.value() === value) {
                              error("新旧密码不能相同");
                              return false
                          }
                          return true
                      }
            } />
            <FormItem ref="repeat_new_password" label="重复新密码" type="password" required="请重复新密码"
                      rule={(value, error, validate) => {
                          if (!validate())
                              return false;
                          if (this.refs.new_password.value() !== value) {
                              error("两次输入的密码不一致");
                              return false
                          }
                          return true
                      }
             } />

            <FormSubmitItem>
                <Button type="primary" htmlType="submit" loading={this.state.pending}>
                    修改
                </Button>
            </FormSubmitItem>
        </form>
    }
}

export default ModifyPasswordForm;