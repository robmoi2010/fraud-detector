import { Route, Switch, Redirect } from 'react-router-dom';
import React, { Component } from 'react';
import SignIn from './components/signin/SignIn';
import SignUp from './components/signin/SignUp';
import SignInService from './components/signin/SignInService';
import MainNavigation from './MainNavigation';
import { withRouter } from 'react-router-dom';
import { config } from './components/configuration/config';
import PermissionService from './components/permission/PermissionService';
import PermissionErrorPage from './components/permission/PermissionErrorPage';
class MainRouter extends Component {
	constructor(props) {
		super(props);
		this.signInService = new SignInService();
		this.permissionService = new PermissionService();
		let auth = false;
		let token = this.signInService.getToken();
		if (token != null) {
			auth = true;
		}
		this.state = { isAuthenticated: auth, userToken: token };

	}
	handleAuthentication = (t, d) => {
		if (d.token != null && d.user != null) {
			this.setState({ isAuthenticated: true, userToken: d });
			this.props.history.push(t);
		}
		else {
			this.setState({ isAuthenticated: false });
		}
	}
	handleRegistration = (r) => {
		this.props.history.push(config.SignInRouteURL);
	}
	render() {
		return (
			<Switch>
				<Route exact path="/" render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.DashboardTabName} />} />
				<Route exact path={config.DashboardRouteURL} render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.DashboardTabName} />} />
				<Route exact path={config.FilesRouteURL} render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.FilesTabName} />} />
				<Route exact path={config.FileTransRouteURL} render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.FileTransTabName} />} />
				<Route exact path={config.FraudulentTransRouteURL} render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.FraudulentTransTabName} />} />
				<Route exact path={config.MissingTransRouteURL} render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.MissingTransTabName} />} />
				<Route exact path={config.MainTransRouteURL} render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.MainTransTabName} />} />
				<Route exact path={config.UsersRouteURL} render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.UsersTabName} />} />
				<Route exact path={config.RolesRouteURL} render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.RolesTabName} />} />
				<Route exact path={config.PaybillsRouteURL} render={(props) => <RouteHelper {...props} isAuthenticated={this.state.isAuthenticated} userToken={this.state.userToken} selectedTab={config.PaybillsTabName} />} />
				<Route exact path="/public" />
				<Route exact path={config.SignInRouteURL} render={(props) => <SignIn {...props} handleAuthentication={this.handleAuthentication} />} />
				<Route exact path={config.SignUpRouteURL} render={(props) => <SignUp {...props} handleRegistration={this.handleRegistration} />} />
			</Switch>
		);
	}
}
export default withRouter(MainRouter);
export function RouteHelper(props) {
	const permissionService = new PermissionService();
	switch (props.selectedTab) {
		case config.DashboardTabName:
			{
				if (props.isAuthenticated) {
					return <MainNavigation {...props} selectedTab={config.DashboardTabName} userToken={props.userToken} />;
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.DashboardRouteURL } }} />;
				}
			}
		case config.FileTransTabName:
			{
				if (props.isAuthenticated) {
					return <MainNavigation {...props} selectedTab={config.FileTransTabName} userToken={props.userToken} />;
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.FileTransRouteURL } }} />;
				}
			}
		case config.FilesTabName:
			{
				if (props.isAuthenticated) {
					return <MainNavigation {...props} selectedTab={config.FilesTabName} userToken={props.userToken} />;
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.FilesRouteURL } }} />;
				}
			}
		case config.FraudulentTransTabName:
			{
				if (props.isAuthenticated) {
					return <MainNavigation {...props} selectedTab={config.FraudulentTransTabName} userToken={props.userToken} />;
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.FraudulentTransRouteURL } }} />;
				}
			}
		case config.MissingTransTabName:
			{
				if (props.isAuthenticated) {
					return <MainNavigation {...props} selectedTab={config.MissingTransTabName} userToken={props.userToken} />;
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.MissingTransRouteURL } }} />;
				}
			}
		case config.MainTransTabName:
			{
				if (props.isAuthenticated) {
					return <MainNavigation {...props} selectedTab={config.MainTransTabName} userToken={props.userToken} />;
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.MainTransRouteURL } }} />;
				}
			}
		case config.UsersTabName:
			{
				if (props.isAuthenticated) {
					if (permissionService.hasRole(props.userToken.user, config.AdminIdentifier, false)) {
						return <MainNavigation {...props} selectedTab={config.UsersTabName} userToken={props.userToken} />;
					}
					else {
						return <PermissionErrorPage {...props} />;
					}
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.UsersRouteURL } }} />;
				}
			}
		case config.RolesTabName:
			{
				if (props.isAuthenticated) {
					if (permissionService.hasRole(props.userToken.user, config.SuperAdminRoleName, true)) {
						return <MainNavigation {...props} selectedTab={config.RolesTabName} userToken={props.userToken} />;
					}
					else {
						return <PermissionErrorPage {...props} />;
					}
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.UsersRouteURL } }} />;
				}
			}
		case config.PaybillsTabName:
			{
				if (props.isAuthenticated) {
					if (permissionService.hasRole(props.userToken.user, config.SuperAdminRoleName, true)) {
						return <MainNavigation {...props} selectedTab={config.PaybillsTabName} userToken={props.userToken} />;
					}
					else {
						return <PermissionErrorPage {...props} />;
					}
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.UsersRouteURL } }} />;
				}
			}
		default:
			{
				if (props.isAuthenticated) {
					return <MainNavigation {...props} selectedTab={config.DashboardTabName} userToken={props.userToken} />;
				}
				else {
					return <Redirect to={{ pathname: config.SignInRouteURL, state: { fromUrl: config.DashboardRouteURL } }} />;
				}
			}
	}
}