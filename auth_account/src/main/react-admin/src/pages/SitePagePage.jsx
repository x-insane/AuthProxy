import HeaderLayout from "../layout/HeaderLayout";
import { Layout } from "antd";
import FooterLayout from "../layout/FooterLayout";
import React from "react";

class SitePagePage extends React.Component {
    render() {
        return <Layout>
            <HeaderLayout text="我的站点和页面" />
            <Layout.Content>

            </Layout.Content>
            <FooterLayout />
        </Layout>
    }
}

export default SitePagePage;