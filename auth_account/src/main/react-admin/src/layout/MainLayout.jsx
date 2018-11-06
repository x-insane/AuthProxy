import React, { Component } from 'react';
import logo from '../logo.svg';

import {Link, Switch, Redirect, Route, withRouter} from 'react-router-dom';
import {Layout, Menu, Icon} from 'antd';
import HomePage from "../pages/HomePage";
import AccountPage from "../pages/AccountPage";

class MainLayout extends Component {
	state = {
		collapsed: false,
	};

	onCollapse = (collapsed) => {
		console.log(collapsed);
		this.setState({ collapsed });
	};

	render() {
		return (
			<Layout style={{ minHeight: '100vh' }}>
				<Layout.Sider
                    collapsible
					collapsed={this.state.collapsed}
					onCollapse={this.onCollapse}
					breakpoint="md"
                    collapsedWidth={0}
				>
					<div className="logo">
						<img src={logo} className="App-logo" alt="logo" />
					</div>

					<Menu theme="dark" defaultSelectedKeys={[
                            this.props.location.pathname === "/" ? "/home" : this.props.location.pathname
                        ]} mode="inline">

						<Menu.Item key="/home">
							<Icon type="user" theme="outlined" />
							<span>个人中心</span>
							<Link to="/home" />
						</Menu.Item>

                        <Menu.Item key="/account">
                            <Icon type="dashboard" theme="outlined" />
                            <span>账户管理</span>
                            <Link to="/account" />
                        </Menu.Item>

						<Menu.Item key="/site-page">
							<Icon type="deployment-unit" theme="outlined" />
							<span>我的站点和页面</span>
							<Link to="/site-page" />
						</Menu.Item>

						<Menu.Item key="/share">
							<Icon type="share-alt" theme="outlined" />
							<span>我的分享</span>
							<Link to="/share" />
						</Menu.Item>

						<Menu.Item key="/invite">
							<Icon type="team" theme="outlined" />
							<span>我的邀请</span>
							<Link to="/invite" />
						</Menu.Item>

						<Menu.Item key="/sub-account">
							<Icon type="user-add" theme="outlined" />
							<span>子账号管理</span>
							<Link to="/sub-account" />
						</Menu.Item>

						<Menu.Item key="/setting">
							<Icon type="setting" theme="outlined" />
							<span>系统管理</span>
							<Link to="/setting" />
						</Menu.Item>

						<Menu.Item key="/manage-user">
							<Icon type="block" theme="outlined" />
							<span>用户管理</span>
							<Link to="/manage-user" />
						</Menu.Item>

						<Menu.Item key="/manage-site">
							<Icon type="ordered-list" theme="outlined" />
							<span>站点管理</span>
							<Link to="/manage-site" />
						</Menu.Item>

						<Menu.Item key="/manage-token">
							<Icon type="check-circle" theme="outlined" />
							<span>Token管理</span>
							<Link to="/manage-token" />
						</Menu.Item>
					</Menu>
				</Layout.Sider>

				<Switch>
                    <Route exact path='/' render={() => <Redirect to={"/home"}/>}/>
                    <Route path="/home" component={HomePage}/>
                    <Route path="/account" component={AccountPage}/>
                </Switch>
			</Layout>
		);
	}
}

export default withRouter(MainLayout);
