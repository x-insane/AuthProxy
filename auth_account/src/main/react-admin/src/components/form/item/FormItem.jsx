import React from "react";

class FormItem extends React.Component {
    state = {
        error: false,
        msg: "",
        value: ""
    };

    onChange = (event) => {
        this.setState({
            error: false,
            value: event.target.value
        });
        // this.validate();
    };

    value = text => {
        if (text === undefined)
            return this.state.value;
        this.setState({
            value: text
        })
    };

    /**
     * 验证输入是否合法，包括简单验证和自定义验证
     * this.props.rule 回调函数：参数1（text），参数2（回调错误信息），参数3（简单验证）
     * @returns {boolean}
     */
    validate = () => {
        const text = this.state.value;
        if (this.props.rule) {
            const isSuccess = this.props.rule(text, (msg) => {
                this.setState({
                    error: true,
                    msg: msg
                });
            }, this.simple_validate.bind(this));
            if (isSuccess) {
                this.setState({
                    error: false
                });
            }
            return isSuccess
        }
        return this.simple_validate()
    };

    /**
     * 简单的验证，包括 required 和 pattern
     * @returns {boolean}
     */
    simple_validate = () => {
        const text = this.state.value;
        if (!text) {
            if (this.props.required) {
                let msg = this.props.required;
                if (msg === true)
                    msg = "请填写" + (this.props.label ? this.props.label : "该字段");
                this.setState({
                    error: true,
                    msg: msg
                });
                return false;
            } else {
                this.setState({
                    error: false
                });
                return true;
            }
        }
        if (this.props.pattern && !this.props.pattern.test(text)) {
            this.setState({
                error: true,
                msg: this.props.explain ? this.props.explain : ("请填写正确的" + this.props.label)
            });
            return false;
        }
        this.setState({
            error: false
        });
        return true;
    };

    render() {
        const id = Math.random().toString(36).substr(2); // 随机id

        const input = <input placeholder={this.props.placeholder || ("请输入" + this.props.label)}
                             type={this.props.type}
                             id={this.props.id || id}
                             name={this.props.name}
                             value={this.state.value}
                             className="ant-input"
                             disabled={this.props.disabled}
                             onChange={this.onChange} />;

        return <div className={["ant-row ant-form-item", this.state.error ? "ant-form-item-with-help" : ""].join(" ")}>
            <div className="ant-form-item-label ant-col-xs-24 ant-col-sm-8">
                <label htmlFor={this.props.id || id} className={this.props.required ? "ant-form-item-required" : ""}>
                    {this.props.label}
                </label>
            </div>
            <div className="ant-form-item-control-wrapper ant-col-xs-24 ant-col-sm-8 fixed-width-input">
                <div className={["ant-form-item-control", this.state.error ? "has-error" : ""].join(" ")}>
                    <span className="ant-form-item-children">
                        {
                            this.props.noinput ? this.props.children : (
                            this.props.addon ? <span className="ant-input-group-wrapper">
                                <span className="ant-input-wrapper ant-input-group">
                                    { input }
                                    <span className="ant-input-group-addon">
                                        { this.props.addon }
                                    </span>
                                </span>
                            </span> : input )
                        }
                    </span>
                    { this.state.error ? <div className="ant-form-explain">{this.state.msg}</div> : null }
                </div>
            </div>
        </div>
    }
}

export default FormItem;