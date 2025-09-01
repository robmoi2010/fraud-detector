/* * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  
 *  This file is part of the Fraud Detector System.
 *  
 *  The Fraud Detector System is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  The Fraud Detector System is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 * */

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