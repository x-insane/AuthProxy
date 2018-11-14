import React from "react";
import { Layout } from "antd";
import HeaderLayout from "../layout/HeaderLayout";
import FooterLayout from "../layout/FooterLayout";

class HomePage extends React.Component {
    render() {
        return <Layout>
            <HeaderLayout text={"个人中心"} />
            <Layout.Content>
            </Layout.Content>
            <FooterLayout />
        </Layout>
    }
}

export default HomePage;