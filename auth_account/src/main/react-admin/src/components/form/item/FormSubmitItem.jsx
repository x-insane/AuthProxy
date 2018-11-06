import React from "react";
import { Form } from "antd";

const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
};

const FormSubmitItem = (props) => <Form.Item {...tailFormItemLayout}>
    {props.children}
</Form.Item>;

export default FormSubmitItem;
