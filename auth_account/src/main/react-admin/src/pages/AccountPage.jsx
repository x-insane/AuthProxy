import React from "react";
import {Layout, Collapse, Form } from "antd";
import HeaderLayout from "../layout/HeaderLayout";
import FooterLayout from "../layout/FooterLayout";
import ModifyPasswordForm from "../components/form/ModifyPasswordForm";
import BindPhoneForm from "../components/form/BindPhoneForm";
import BindEmailForm from "../components/form/BindEmailForm";

const { Panel } = Collapse;

class AccountPage extends React.Component {
    render() {
        return <Layout>
            <HeaderLayout text="账户管理" />
            <Layout.Content>
                <Collapse accordion defaultActiveKey={"1"}>
                    <Panel header="修改密码" key="1">
                        <ModifyPasswordForm />
                    </Panel>
                    <Panel header="绑定手机" key="2">
                        <BindPhoneForm />
                    </Panel>
                    <Panel header="绑定邮箱" key="3">
                        <BindEmailForm />
                    </Panel>
                </Collapse>
            </Layout.Content>
            <FooterLayout />
        </Layout>
    }
}

export default Form.create()(AccountPage);