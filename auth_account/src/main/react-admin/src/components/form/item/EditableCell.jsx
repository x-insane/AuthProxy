import React from "react";
import {Form, Input} from "antd";

const FormItem = Form.Item;

class EditableCell extends React.Component {
    state = {
        editing: false,
    };

    componentDidMount() {
        if (this.props.editable)
            document.addEventListener('click', this.handleClickOutside, true);
        const { dataIndex, record } = this.props;
        if (record && record[dataIndex])
            this.text = record[dataIndex]
    }

    componentWillUnmount() {
        if (this.props.editable)
            document.removeEventListener('click', this.handleClickOutside, true)
    }

    toggleEdit = () => {
        const editing = !this.state.editing;
        this.setState({ editing }, () => {
            if (editing)
                this.input.focus()
        });
    };

    handleClickOutside = (e) => {
        const { editing } = this.state;
        if (editing && this.cell !== e.target && !this.cell.contains(e.target))
            this.save()
    };

    save = () => {
        const { record, onSave } = this.props;
        this.toggleEdit();
        if (onSave) {
            onSave({
                ...record,
                [this.props.dataIndex]: this.text
            }, this.text, this.props.dataIndex)
        }
    };

    render() {
        const { editing } = this.state;
        const {
            editable,
            dataIndex,
            title,
            record,
            index,
            onSave,
            ...restProps
        } = this.props;
        return (
            <td ref={node => (this.cell = node)} {...restProps}>
                {
                    editable ? (
                        editing ? (
                            <FormItem style={{ margin: 0 }}>
                                <Input
                                    ref={node => this.input = node}
                                    defaultValue={this.text}
                                    onChange={e => this.text = e.target.value}
                                    onPressEnter={this.save}
                                />
                            </FormItem>
                        ) : (
                            <div
                                className="editable-cell-value-wrap"
                                style={{ paddingRight: 24 }}
                                onClick={this.toggleEdit}
                            >
                                { restProps.children }
                            </div>
                        )
                    ) : restProps.children
                }
            </td>
        );
    }
}

export default EditableCell;