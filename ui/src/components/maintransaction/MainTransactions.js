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
import MainTransactionsService from './MainTransactionsService';
import { DataGrid, GridToolbarContainer, GridToolbarDensitySelector, GridToolbarExport } from '@material-ui/data-grid';
import { Redirect } from 'react-router-dom';
import { config } from '../configuration/config';
import SignInService from '../signin/SignInService';
class MainTransactions extends React.Component {
    constructor(props) {
        super(props);
        this.mainTransactionsService = new MainTransactionsService();
        this.signInService = new SignInService();
        this.state = { redirectTo: null, redirect: false, density: "compact", filterModel: { items: [] }, sortModel: [{ field: "createdOn", sort: "desc" }], transactions: { count: 0, mpesaMainTransactions: [] }, page: 0, rowsPerPage: 10, loading: true, rowsPerPageOptions: [10, 15, 20, 25, 50, 100] };
    }
    componentDidMount() {
        this.getMainTransactions(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
    }
    componentDidUpdate(prevProp, prevState) {
        if (prevState !== null) {
            if (prevState.page !== this.state.page || prevState.rowsPerPage !== this.state.rowsPerPage || prevState.sortModel !== this.state.sortModel || prevState.filterModel !== this.state.filterModel) {
                this.getMainTransactions(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
            }
        }
    }
    render() {
        const columns = [
            { field: 'confirmationId', headerName: 'ID', hide: true },
            { field: 'createdOn', headerName: 'Created On', flex: 1, headerAlign: 'center' },
            { field: 'msisdn', headerName: 'Msisdn', flex: 1, headerAlign: 'center' },
            { field: 'firstname', headerName: 'Firstname', flex: 1, headerAlign: 'center' },
            { field: 'lastname', headerName: 'Lastname', flex: 1, headerAlign: 'center' },
            { field: 'transAmount', headerName: 'Amount', flex: 1, headerAlign: 'center' },
            { field: 'transId', headerName: 'ID', flex: 1, headerAlign: 'center' },
            { field: 'businessShortcode', headerName: 'Shortcode', flex: 1, headerAlign: 'center' },
            { field: 'transTime', headerName: 'Transaction Time', flex: 1, headerAlign: 'center' }
        ];
        if (!this.state.redirect) {
            return (
                <DataGrid components={{ Toolbar: this.CustomToolbar }} sortingOrder={['desc', 'asc']} sortingMode="server" sortModel={this.state.sortModel} onSortModelChange={(ev) => this.handleSortModelChange(ev)} rowsPerPageOptions={this.state.rowsPerPageOptions} getRowId={(r) => r.confirmationId} rows={this.state.transactions.mpesaMainTransactions} rowCount={this.state.transactions.count} columns={columns} onPageSizeChange={(ev) => this.handlePageSizeChange(ev)} onPageChange={(ev) => this.handlePageChange(ev)} density={this.state.density} pagination paginationMode="server" page={this.state.page} pageSize={this.state.rowsPerPage} loading={this.state.loading} autoHeight={true} filterMode="server" onFilterModelChange={(ev) => this.handleOnFilterChange(ev)} />
            );
        }
        else {
            if (this.state.redirectTo === "signin") {
                return (
                    <Redirect to={{
                        pathname: config.SignInRouteURL,
                        state: { fromUrl: config.MainTransRouteURL }
                    }} />
                );
            }
        }
    }
    getMainTransactions = (page, pageSize, sortingMode, filterModel) => {
        var f = sortingMode[0];
        this.mainTransactionsService.retrieveMainTransactions(page, pageSize, f.field, f.sort, filterModel, this.props.accessToken).then(data => {
            if (data != null) {
                if (data.error != null) {
                    if (data.error === config.InvalidTokenResp) {
                        this.signInService.clearData();
                        this.setState({ redirectTo: "signin", redirect: true });
                    }
                    else {
                        alert(data.error);
                        this.setState({ loading: false });
                    }
                }
                else {
                    this.setState({ transactions: data, loading: false });
                }
            }
            else {
                this.setState({ loading: false });
            }
        });
    }
    handlePageChange = (ev) => {
        this.setState({ page: ev, loading: true });
    }
    handlePageSizeChange = (ev) => {
        this.setState({ rowsPerPage: ev, page: 0, loading: true });
    }
    handleOnFilterChange = (ev) => {
        this.setState({ filterModel: ev, page: 0, loading: true });
    }
    handleSortModelChange = (ev) => {
        if(JSON.stringify(this.state.sortModel)!==JSON.stringify(ev))
        {
        this.setState({ sortModel: ev, page: 0, loading: true });
        }
    }
    CustomToolbar = () => {
        return (
            <GridToolbarContainer>
                <GridToolbarDensitySelector />
                <GridToolbarExport />
            </GridToolbarContainer>
        );
    }
}
export default MainTransactions;