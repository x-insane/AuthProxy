import React from "react";
import {Layout, Form, Popconfirm, Button, Table, Input, Spin, message} from "antd";
import HeaderLayout from "../layout/HeaderLayout";
import FooterLayout from "../layout/FooterLayout";
import EditableCell from "../components/form/item/EditableCell";
import post from "../helper/ApiHelper";

const EditableContext = React.createContext();
const EditableContextCell = props => <EditableCell context={EditableContext} {...props} />;

const EditableRow = ({ form, index, ...props }) => (
    <EditableContext.Provider value={form}>
        <tr {...props} />
    </EditableContext.Provider>
);

const EditableFormRow = Form.create()(EditableRow);

class SettingPage extends React.Component {
    columns = [{
            title: '#',
            dataIndex: 'id',
            width: '5%',
            align: "right",
        }, {
            title: 'key',
            dataIndex: 'key',
            width: "20%"
        }, {
            title: 'value',
            dataIndex: 'value',
            width: "60%",
            editable: true,
        }, {
            title: '操作',
            dataIndex: 'operation',
            align: "center",
            width: "10%",
            render: (text, record) => {
                return (
                    this.state.dataSource.length >= 1
                        ? (
                            <Popconfirm cancelText="取消" okText="删除" title="删除不可恢复，确认删除?"
                                        onConfirm={() => this.handleDelete(record.key)}>
                                <span className="like-a-link">删除</span>
                            </Popconfirm>
                        ) : null
                );
            },
        }];

    state = {
        dataSource: null,
        pending: false
    };

    componentDidMount() {
        post("/api/admin/config/query_all")
        .then(res => {
            if (res.error === 0) {
                this.setState({
                    dataSource: res.configs
                });
            } else message.error(res.msg)
        })
        .catch(() => {
            message.error("网络错误")
        })
    }

    handleDelete = (key) => {
        this.setState({
            pending: true
        });

        post("/api/admin/config/delete", {
            key: key
        })
        .then(res => {
            if (res.error === 0) {
                const dataSource = [...this.state.dataSource];
                this.setState({ dataSource: dataSource.filter(item => item.key !== key) })
            } else message.error(res.msg)
        })
        .catch(() => {
            message.error("网络错误")
        })
        .finally(() => {
            this.setState({
                pending: false
            });
        })
    };

    handleAdd = () => {
        const { dataSource } = this.state;

        if (!this.new_key)
            return message.error("请输入key");

        let exists = false;
        dataSource.forEach(item => {
            if (item.key === this.new_key)
                exists = true
        });
        if (exists)
            return message.error("该key已存在");

        this.setState({
            pending: true
        });

        post("/api/admin/config", {
            key: this.new_key,
            value: ""
        })
        .then(res => {
            if (res.error === 0) {
                this.setState({
                    dataSource: [...dataSource, res.config]
                })
            } else message.error(res.msg)
        })
        .catch(() => {
            message.error("网络错误")
        })
        .finally(() => {
            this.setState({
                pending: false
            });
        })
    };

    handleSave = (row) => {
        const newData = [...this.state.dataSource];
        const index = newData.findIndex(item => row.key === item.key);
        const item = newData[index];
        if (item.value === row.value)
            return;

        this.setState({
            pending: true
        });

        post("/api/admin/config", {
            key: row.key,
            value: row.value
        })
        .then(res => {
            if (res.error === 0) {
                newData.splice(index, 1, {
                    ...item,
                    ...res.config,
                });
                this.setState({ dataSource: newData });
            } else message.error(res.msg)
        })
        .catch(() => {
            message.error("网络错误")
        })
        .finally(() => {
            this.setState({
                pending: false
            });
        })
    };

    render() {
        const { dataSource } = this.state;
        const components = {
            body: {
                row: EditableFormRow,
                cell: EditableContextCell,
            },
        };
        const columns = this.columns.map((col) => {
            if (!col.editable)
                return col;
            return {
                ...col,
                onCell: record => ({
                    record,
                    editable: col.editable,
                    dataIndex: col.dataIndex,
                    title: col.title,
                    onSave: this.handleSave,
                }),
            };
        });

        return <Layout>
            <HeaderLayout text="系统管理" />
            <Layout.Content>
                {
                    this.state.pending ? <div className="global-cover">
                        <Spin size="large" />
                    </div> : null
                }
                {
                    dataSource === null ? <div style={{ textAlign: "center", marginTop: 15 }}>
                        <Spin />
                    </div> : <div>
                        <Form layout="inline" onSubmit={this.handleAdd} style={{ marginTop: 16, marginBottom: 16, textAlign: "right" }}>
                            <Form.Item>
                                <Input placeholder="key" onChange={e => this.new_key = e.target.value} />
                            </Form.Item>
                            <Form.Item>
                                <Button type="primary" htmlType="submit">
                                    添加一项
                                </Button>
                            </Form.Item>
                        </Form>
                        <Table
                            components={components}
                            rowClassName={() => 'editable-row'}
                            bordered
                            dataSource={dataSource}
                            columns={columns}
                        />
                    </div>
                }
            </Layout.Content>
            <FooterLayout />
        </Layout>
    }
}

export default SettingPage;