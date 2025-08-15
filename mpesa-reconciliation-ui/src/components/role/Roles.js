import React from 'react';
import RolesService from './RolesService';
import { config } from '../configuration/config';
import { Redirect } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import SignInService from '../signin/SignInService';
import { DataGrid, GridToolbarContainer, GridToolbarExport, GridToolbarDensitySelector, GridToolbarColumnsButton } from '@material-ui/data-grid';
import AddRoleForm from './AddRoleForm';
class Roles extends React.Component {
	constructor(props) {
		super(props);
		this.rolesService = new RolesService();
		this.signInService = new SignInService();
		this.state = { formOpen: false, redirectTo: null, redirect: false, density: "compact", filterModel: { items: [] }, sortModel: [{ field: "name", sort: "desc" }], roles: { count: 0, mpesaRole: [] }, page: 0, rowsPerPage: 10, loading: true, rowsPerPageOptions: [10, 15, 20, 25, 50, 100] };
	}
	componentDidMount() {
		this.getRoles(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
	}
	componentDidUpdate(prevProp, prevState) {
		if (prevState !== null) {
			if (prevState.page !== this.state.page || prevState.rowsPerPage !== this.state.rowsPerPage || prevState.sortModel !== this.state.sortModel || prevState.filterModel !== this.state.filterModel) {
				this.getRoles(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
			}
		}
	}
	handleClickOpen = () => {
		this.setState({ formOpen: true });
	}
	handleCloseForm = () => {
		this.setState({ formOpen: false });
	}
	render() {
		if (!this.state.redirect) {
			const columns = [
				{ field: 'roleId', headerName: 'ID', hide: true },
				{ field: 'createdOn', headerName: 'Created On', flex: 1, headerAlign: 'center' },
				{ field: 'name', headerName: 'Name', flex: 1, headerAlign: 'center' }
			];
			return (
				<div>
					<Button variant="outlined" color="primary" onClick={this.handleClickOpen}>
						Add Role
                    </Button>
					<AddRoleForm {...this.props} handleCloseForm={this.handleCloseForm} formOpen={this.state.formOpen} />
					<DataGrid components={{ Toolbar: this.CustomToolbar }} sortingOrder={['desc', 'asc']} sortingMode="server" sortModel={this.state.sortModel} onSortModelChange={(ev) => this.handleSortModelChange(ev)} rowsPerPageOptions={this.state.rowsPerPageOptions} getRowId={(r) => r.roleId} rows={this.state.roles.mpesaRole} rowCount={this.state.roles.count} columns={columns} onPageSizeChange={(ev) => this.handlePageSizeChange(ev)} onPageChange={(ev) => this.handlePageChange(ev)} density={this.state.density} pagination paginationMode="server" page={this.state.page} pageSize={this.state.rowsPerPage} loading={this.state.loading} autoHeight={true} onFilterModelChange={(ev) => this.handleOnFilterChange(ev)} />
				</div >
			);
		}
		else {
			if (this.state.redirectTo === "signin") {
				return (
					<Redirect to={{
						pathname: config.SignInRouteURL,
						state: { fromUrl: config.RolesRouteURL }
					}} />
				);
			}
		}
	}
	getRoles = (page, pageSize, sortModel, filterModel) => {
		try {
			var f = sortModel[0];
			this.rolesService.retrieveRoles(page, pageSize, f.field, f.sort, filterModel, this.props.accessToken).then(data => {
				console.log(data);
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
						this.setState({ roles: data, loading: false });
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
		this.setState({ page: ev.page, loading: true });
	}
	handlePageSizeChange = (ev) => {
		this.setState({ rowsPerPage: ev.pageSize, page: 0, loading: true });
	}
	handleOnFilterChange = (ev) => {
		this.setState({ filterModel: ev.filterModel, page: 0, loading: true });
	}
	handleSortModelChange = (ev) => {
		this.setState({ sortModel: ev.sortModel, page: 0, loading: true });
	}
	CustomToolbar = () => {
		return (
			<GridToolbarContainer>
				<GridToolbarDensitySelector />
				<GridToolbarExport />
				<GridToolbarColumnsButton />
			</GridToolbarContainer>
		);
	}

}
export default Roles;