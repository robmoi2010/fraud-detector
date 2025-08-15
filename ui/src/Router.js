import React from 'react';
import Dashboard from './components/dashboard/Dashboard';
import File from './components/file/Files';
import FileTransactions from './components/filetransaction/FileTransactions';
import FraudulentTransactions from './components/fraudulenttransaction/FraudulentTransactions';
import MissingTransactions from './components/missingtransaction/MissingTransactions';
import MainTransactions from './components/maintransaction/MainTransactions';
import Users from './components/users/Users';
import { config } from './components/configuration/config';
import Roles from './components/role/Roles';
import Paybills from './components/paybill/Paybills';
class Router extends React.Component {
    render() {
        switch (this.props.currentComponent) {
            case config.DashboardTabName: {
                return (<Dashboard {...this.props} accessToken={this.props.accessToken} />);
            }
            case config.FilesTabName: {
                return (<File {...this.props} accessToken={this.props.accessToken} />);
            }
            case config.FileTransTabName: {
                return (<FileTransactions {...this.props} accessToken={this.props.accessToken} />);
            }
            case config.FraudulentTransTabName: {
                return (<FraudulentTransactions {...this.props} accessToken={this.props.accessToken} />);
            }
            case config.MissingTransTabName: {
                return (<MissingTransactions {...this.props} accessToken={this.props.accessToken} />);
            }
            case config.MainTransTabName: {
                return (<MainTransactions {...this.props} accessToken={this.props.accessToken} />);
            }
            case config.UsersTabName: {
                return (<Users {...this.props} accessToken={this.props.accessToken} />);
            }
            case config.RolesTabName: {
                return (<Roles {...this.props} accessToken={this.props.accessToken} />);
            }
            case config.PaybillsTabName: {
                return (<Paybills {...this.props} accessToken={this.props.accessToken} />);
            }
            default: {
                return (<Dashboard {...this.props} accessToken={this.props.accessToken} />);
            }

        }
    }
}
export default Router;