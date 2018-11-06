import React from "react";
import { Layout } from "antd";

export default (props) =>
    <Layout.Header style={{ background: '#fff'}}>
        <h2>{props.text}</h2>
    </Layout.Header>
