import React, { Component } from 'react';
import './App.css';

import { HashRouter as Router } from 'react-router-dom';
import MainLayout from './layout/MainLayout';

class App extends Component {
	render() {
		return (
			<Router>
				<MainLayout />
			</Router>
		);
	}
}

export default App;
