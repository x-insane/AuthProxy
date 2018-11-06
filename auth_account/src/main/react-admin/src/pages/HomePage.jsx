import React from "react";
import { Layout } from "antd";
import HeaderLayout from "../layout/HeaderLayout";
import FooterLayout from "../layout/FooterLayout";

class HomePage extends React.Component {
    render() {
        return <Layout>
            <HeaderLayout text={"个人中心"} />
            <Layout.Content style={{ margin: '0 16px' }}>
                <div style={{ padding: 24, background: '#fff', minHeight: 360 }}>
                    Bill is a cat.
                </div>
            </Layout.Content>
            <FooterLayout />
        </Layout>
    }
}

export default HomePage;