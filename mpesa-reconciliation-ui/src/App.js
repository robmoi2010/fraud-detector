import { BrowserRouter as Router } from 'react-router-dom';
import MainRouter from './MainRouter';
import React from 'react';
class App extends React.Component {
	render() {
		return (
			<Router>
				<MainRouter />
			</Router>
		);
	}
}
export default App;