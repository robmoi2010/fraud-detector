import React from 'react';
import FileTransactionsService from './FileTransactionsService';
import { config } from '../configuration/config';
import { Redirect } from 'react-router-dom';
import SignInService from '../signin/SignInService';
import { DataGrid, GridToolbarContainer, GridToolbarExport, GridToolbarDensitySelector } from '@material-ui/data-grid';
class FileTransactions extends React.Component {
    constructor(props) {
        super(props);
        this.fileTransactionsService = new FileTransactionsService();
        this.signInService = new SignInService();
        this.state = { redirectTo: null, redirect: false, density: "compact", filterModel: { items: [] }, sortModel: [{ field: "createdDate", sort: "desc" }], transactions: { count: 0, mpesaTransactions: [] }, page: 0, rowsPerPage: 10, loading: true, rowsPerPageOptions: [10, 15, 20, 25, 50, 100] };
    }
    componentDidMount() {
        this.getFileTransactions(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
    }
    componentDidUpdate(prevProp, prevState) {
        if (prevState !== null) {
            alert(JSON.stringify(prevState.sortModel));
            alert(JSON.stringify(this.state.sortModel));
            if (prevState.page !== this.state.page || prevState.rowsPerPage !== this.state.rowsPerPage || JSON.stringify(prevState.sortModel) !== JSON.stringify(this.state.sortModel) || prevState.filterModel !== this.state.filterModel) {
                this.getFileTransactions(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
            }
        }
    }
    render() {
        if (!this.state.redirect) {
            const columns = [
                { field: 'fetchId', headerName: 'ID', hide: true },
                { field: 'createdDate', headerName: 'Created On', flex: 1, headerAlign: 'center' },
                { field: 'receiptNo', headerName: 'Transaction ID', flex: 1, headerAlign: 'center' },
                { field: 'completionTime', headerName: 'Transaction Time', flex: 1, headerAlign: 'center' },
                { field: 'firstname', headerName: 'First Name', flex: 1, headerAlign: 'center' },
                { field: 'lastname', headerName: 'Last Name', flex: 1, headerAlign: 'center' },
                { field: 'paidIn', headerName: 'Amount', flex: 0.7, headerAlign: 'center' },
                { field: 'msisdn', headerName: 'Msisdn', flex: 1, headerAlign: 'center' }
            ];
            return (
                <DataGrid components={{ Toolbar: this.CustomToolbar }} sortingOrder={['desc', 'asc']} sortingMode="server" sortModel={this.state.sortModel} onSortModelChange={(ev) => this.handleSortModelChange(ev)} rowsPerPageOptions={this.state.rowsPerPageOptions} getRowId={(r) => r.fetchId} rows={this.state.transactions.mpesaTransactions} rowCount={this.state.transactions.count} columns={columns} onPageSizeChange={(ev) => this.handlePageSizeChange(ev)} onPageChange={(ev) => this.handlePageChange(ev)} density={this.state.density} pagination paginationMode="server" page={this.state.page} pageSize={this.state.rowsPerPage} loading={this.state.loading} autoHeight={true} onFilterModelChange={(ev) => this.handleOnFilterChange(ev)} />
            );
        }
        else {
            if (this.state.redirectTo === "signin") {
                return (
                    <Redirect to={{
                        pathname: config.SignInRouteURL,
                        state: { fromUrl: config.FileTransRouteURL }
                    }} />
                );
            }
        }
    }
    getFileTransactions = (page, pageSize, sortModel, filterModel) => {
        try {
            var f = sortModel[0];
            this.fileTransactionsService.retrieveFileTransactions(page, pageSize, f.field, f.sort, filterModel, this.props.accessToken).then(data => {
                if (data != null) {
                    if (data.error != null) {
                        if (data.error === config.InvalidTokenResp) {
                            this.signInService.clearData();
                            this.setState({ redirectTo: "signin", redirect: true });
                        }
                        else {
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
        alert(ev);
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
export default FileTransactions;