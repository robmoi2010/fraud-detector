import React from 'react';
import FileService from './FileService';
import Button from "@material-ui/core/Button";
import { DataGrid, GridToolbarContainer, GridToolbarDensitySelector, GridToolbarExport } from '@material-ui/data-grid';
import { Redirect } from 'react-router-dom';
import { config } from '../configuration/config';
import SignInService from '../signin/SignInService';
class Files extends React.Component {
    constructor(props) {
        super(props);
        this.fileService = new FileService();
        this.signInService = new SignInService();
        this.state = { redirectTo: null, selectedId: 0, redirect: false, density: "compact", filterModel: null, sortModel: [{ field: "createdDate", sort: "desc" }], files: { count: 0, mpesaFiles: [] }, page: 0, rowsPerPage: 10, loading: true, rowsPerPageOptions: [10, 15, 20, 25, 50, 100] };
    }
    componentDidMount() {
        this.getFiles(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
    }
    componentDidUpdate(prevProp, prevState) {
        if (prevState !== null) {
            if (prevState.page !== this.state.page || prevState.rowsPerPage !== this.state.rowsPerPage || prevState.sortModel !== this.state.sortModel || prevState.filterModel !== this.state.filterModel) {
                this.getFiles(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
            }
        }
    }
    render() {
        const columns = [
            { field: 'fileId', headerName: 'ID', hide: true },
            { field: 'createdDate', headerName: 'Created On', flex: 1, headerAlign: 'center' },
            { field: 'fileName', headerName: 'File Name', flex: 1, headerAlign: 'center' },
            { field: 'transCount', headerName: 'Total Transactions', flex: 1, headerAlign: 'center' },
            { field: 'processed', headerName: 'Processed', flex: 1, headerAlign: 'center' },
            { field: 'dateProcessed', headerName: 'Processed On', flex: 1, headerAlign: 'center' },
            { field: 'shortcode', headerName: 'Shortcode', flex: 1, headerAlign: 'center' },
            {
                field: "",
                headerName: "Action",
                flex: 2,
                headerAlign: 'center',
                disableClickEventBubbling: true,
                renderCell: (params) => (
                    <strong>
                        <Button
                            variant="contained"
                            color="primary"
                            size="small"
                            style={{ marginLeft: 2 }}
                            onClick={() => this.handleViewTransButton(params.id)}>
                            View Transactions
                      </Button>
                    </strong>
                )
            }
        ];
        if (!this.state.redirect) {
            return (
                <DataGrid components={{ Toolbar: this.CustomToolbar }} sortingOrder={['desc', 'asc']} sortingMode="server" sortModel={this.state.sortModel} onSortModelChange={(ev) => this.handleSortModelChange(ev)} rowsPerPageOptions={this.state.rowsPerPageOptions} getRowId={(r) => r.fileId} rows={this.state.files.mpesaFiles} rowCount={this.state.files.count} columns={columns} onPageSizeChange={(ev) => this.handlePageSizeChange(ev)} onPageChange={(ev) => this.handlePageChange(ev)} density={this.state.density} pagination paginationMode="server" page={this.state.page} pageSize={this.state.rowsPerPage} loading={this.state.loading} autoHeight={true} filterMode="server" onFilterModelChange={(ev) => this.handleOnFilterChange(ev)} />
            );
        }
        else {
            if (this.state.redirectTo === "filetransactions") {
                return (
                    <Redirect to={{
                        pathname: config.FileTransRouteURL,
                        state: { selectedFileId: this.state.selectedId }
                    }} />
                );
            }
            else if (this.state.redirectTo === "signin") {
                return (
                    <Redirect to={{
                        pathname: config.SignInRouteURL,
                        state: { fromUrl: config.FilesRouteURL }
                    }} />
                );
            }
        }
    }
    getFiles = (page, pageSize, sortingMode, filterModel) => {
        var f = sortingMode[0];
        var filterObj = null;
        if (filterModel != null) {
            filterObj = filterModel.items[0];
        }
        this.fileService.retrieveFiles(page, pageSize, f.field, f.sort, filterObj, this.props.accessToken).then(data => {
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
                    this.setState({ files: data, loading: false });
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
    handleViewTransButton = (e) => {
        this.setState({ redirectTo: "filetransactions", selectedId: e, redirect: true });
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
export default Files;