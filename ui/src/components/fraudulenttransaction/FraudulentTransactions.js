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
import FraudulentTransactionsService from './FraudulentTransactionsService';
import SignInService from '../signin/SignInService';
import { Redirect } from 'react-router-dom';
import { config } from '../configuration/config';
import { DataGrid, GridToolbarContainer, GridToolbarExport, GridToolbarDensitySelector } from '@material-ui/data-grid';
class FraudulentTransactions extends React.Component {
	constructor(props) {
		super(props);
		this.fraudulentTransactionsService = new FraudulentTransactionsService();
		this.signInService = new SignInService();
		let defaultFilters = [{ columnField: 'isValidTrans', value: false, operatorValue: 'equals' }, { columnField: 'discrepancyType', value: 2, operatorValue: 'equals' }]
		let selectedFileId = 0;
		if (this.props.location != null) {
			if (this.props.location.state != null) {
				if (this.props.location.state.selectedFileId != null) {
					selectedFileId = this.props.location.state.selectedFileId;
				}

			}
		}
		this.state = { defaultFilters: defaultFilters, redirectTo: null, redirect: false, fileId: selectedFileId, density: "compact", filterModel: { items: defaultFilters }, sortModel: [{ field: "createdDate", sort: "desc" }], transactions: { count: 0, mpesaReconTransactions: [] }, page: 0, rowsPerPage: 10, loading: true, rowsPerPageOptions: [10, 15, 20, 25, 50, 100] };
	}
	componentDidMount() {
		this.getFraudulentTransactions(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
	}
	componentDidUpdate(prevProp, prevState) {
		if (prevState !== null) {
			if (prevState.page !== this.state.page || prevState.rowsPerPage !== this.state.rowsPerPage || prevState.sortModel !== this.state.sortModel || prevState.filterModel !== this.state.filterModel) {
				this.getFraudulentTransactions(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
			}
		}
	}
	render() {
		if (!this.state.redirect) {
			const columns = [
				{ field: 'reconId', headerName: 'ID', hide: true },
				{ field: 'createdDate', headerName: 'Created On', flex: 1, headerAlign: 'center' },
				{ field: 'mpesaMainTransactions.transId', headerName: 'Transaction ID', flex: 1, headerAlign: 'center', valueGetter: (params) => { return params.row.mpesaMainTransactions.transid } },
				{ field: 'mpesaMainTransactions.transTime', headerName: 'Transaction Time', flex: 1, headerAlign: 'center', valueGetter: (params) => { return params.row.mpesaMainTransactions.transTime } },
				{ field: 'mpesaMainTransactions.firstname', headerName: 'First Name', flex: 1, headerAlign: 'center', valueGetter: (params) => { return params.row.mpesaPaymenmpesaMainTransactionstConfirmation.firstname } },
				{ field: 'mpesaMainTransactions.lastname', headerName: 'Last Name', flex: 1, headerAlign: 'center', valueGetter: (params) => { return params.row.mpesaMainTransactions.lastname } },
				{ field: 'mpesaMainTransactions.transAmount', headerName: 'Amount', flex: 0.7, headerAlign: 'center', valueGetter: (params) => { return params.row.mpesaMainTransactions.transAmount } },
				{ field: 'mpesaMainTransactions.msisdn', headerName: 'Msisdn', flex: 1, headerAlign: 'center', valueGetter: (params) => { return params.row.mpesaMainTransactions.msisdn } },
				{ field: 'comments', headerName: 'Comment', flex: 1, headerAlign: 'center' },
				{ field: 'action', headerName: 'Action', flex: 1, headerAlign: 'center' }
			];
			return (
				<DataGrid components={{ Toolbar: this.CustomToolbar }} sortingOrder={['desc', 'asc']} sortingMode="server" sortModel={this.state.sortModel} onSortModelChange={(ev) => this.handleSortModelChange(ev)} rowsPerPageOptions={this.state.rowsPerPageOptions} getRowId={(r) => r.reconId} rows={this.state.transactions.mpesaReconTransactions} rowCount={this.state.transactions.count} columns={columns} onPageSizeChange={(ev) => this.handlePageSizeChange(ev)} onPageChange={(ev) => this.handlePageChange(ev)} density={this.state.density} pagination paginationMode="server" page={this.state.page} pageSize={this.state.rowsPerPage} loading={this.state.loading} autoHeight={true} onFilterModelChange={(ev) => this.handleOnFilterChange(ev)} />
			);
		}
		else {
			if (this.state.redirectTo === "signin") {
				return (
					<Redirect to={{
						pathname: config.SignInRouteURL,
						state: { fromUrl: config.FraudulentTransRouteURL }
					}} />
				);
			}
		}
	}
	getFraudulentTransactions = (page, pageSize, sortModel, filterModel) => {
		try {
			var f = sortModel[0];
			if (this.state.fileId !== 0) {
				if (filterModel == null) {
					filterModel = { items: [] };
				}
				filterModel.items.push({ columnField: 'fileId', value: this.state.fileId, operatorValue: 'equals' });
			}
			this.fraudulentTransactionsService.retrieveFraudulentTransactions(page, pageSize, f.field, f.sort, filterModel, this.props.accessToken, this.state.fileId).then(data => {
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
		catch (error) {
			alert(error);
		}
	}
	handlePageChange = (ev) => {
		this.setState({ page: ev, loading: true });
	}
	handlePageSizeChange = (ev) => {
		this.setState({ rowsPerPage: ev, page: 0, loading: true });
	}
	handleOnFilterChange = (ev) => {
		this.setState({ filterModel: ev.filterModel.items.push(this.state.defaultFilters), page: 0, loading: true });
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
export default FraudulentTransactions;