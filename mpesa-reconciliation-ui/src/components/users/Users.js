import React from 'react';
import Button from "@material-ui/core/Button";
import { DataGrid, GridToolbarContainer, GridToolbarDensitySelector, GridToolbarExport } from '@material-ui/data-grid';
import { Redirect } from 'react-router-dom';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import { config } from '../configuration/config';
import UsersService from './UsersService';
class Users extends React.Component {
	constructor(props) {
		super(props);
		this.usersService = new UsersService();
		this.state = { selectedUser: null, anchorEl: null, redirectTo: null, selectedId: 0, redirect: false, density: "compact", filterModel: { items: [] }, sortModel: [{ field: "createdOn", sort: "desc" }], users: { count: 0, mpesaUser: [] }, page: 0, rowsPerPage: 10, loading: true, rowsPerPageOptions: [10, 15, 20, 25, 50, 100] };
	}
	componentDidMount() {
		this.getUsers(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
	}
	componentDidUpdate(prevProp, prevState) {
		if (prevState !== null) {
			if (prevState.page !== this.state.page || prevState.rowsPerPage !== this.state.rowsPerPage || prevState.sortModel !== this.state.sortModel || prevState.filterModel !== this.state.filterModel) {
				this.getUsers(this.state.page, this.state.rowsPerPage, this.state.sortModel, this.state.filterModel);
			}
		}
	}
	render() {
		const columns = [
			{ field: 'userId', headerName: 'ID', hide: true },
			{ field: 'createdOn', headerName: 'Created On', flex: 1, headerAlign: 'center' },
			{ field: 'email', headerName: 'Email', flex: 1, headerAlign: 'center' },
			{ field: 'firstName', headerName: 'First Name', flex: 1, headerAlign: 'center' },
			{ field: 'lastName', headerName: 'Last Name', flex: 1, headerAlign: 'center' },
			{ field: 'active', headerName: 'Active', flex: 1, headerAlign: 'center' },
			{ field: 'accountVerified', headerName: 'Account Verified', flex: 1, headerAlign: 'center' },
			{ field: 'loginDisabled', headerName: 'Login Disabled', flex: 1, headerAlign: 'center' },
			{
				field: "",
				headerName: "Action",
				flex: 2,
				headerAlign: 'center',
				disableClickEventBubbling: true,
				renderCell: (params) => (
					<div>
						<Button aria-controls="simple-menu" aria-haspopup="true" onClick={(event) => this.handleClick(params, event)}>
							Action
                        </Button>
						<Menu
							id="simple-menu"
							anchorEl={this.state.anchorEl}
							keepMounted
							open={Boolean(this.state.anchorEl)}
							onClose={() => this.handleClose("close")}
						>
							<MenuItem onClick={() => this.handleClose("activate")}>Activate</MenuItem>
							<MenuItem onClick={() => this.handleClose("assignroles")}>Assign Roles</MenuItem>
							<MenuItem onClick={() => this.handleClose("delete")}>Delete</MenuItem>
						</Menu>
					</div>
				)
			}
		];
		if (!this.state.redirect) {
			return (
				<DataGrid components={{ Toolbar: this.CustomToolbar }} sortingOrder={['desc', 'asc']} sortingMode="server" sortModel={this.state.sortModel} onSortModelChange={(ev) => this.handleSortModelChange(ev)} rowsPerPageOptions={this.state.rowsPerPageOptions} getRowId={(r) => r.userId} rows={this.state.users.mpesaUser} rowCount={this.state.users.count} columns={columns} onPageSizeChange={(ev) => this.handlePageSizeChange(ev)} onPageChange={(ev) => this.handlePageChange(ev)} density={this.state.density} pagination paginationMode="server" page={this.state.page} pageSize={this.state.rowsPerPage} loading={this.state.loading} autoHeight={true} filterMode="server" onFilterModelChange={(ev) => this.handleOnFilterChange(ev)} />
			);
		}
		else {
			if (this.state.redirectTo === "signin") {
				return (
					<Redirect to={{
						pathname: config.SignInRouteURL,
						state: { fromUrl: config.UsersRouteURL }
					}} />
				);
			}
		}
	}
	handleClick = (params, event) => {
		this.setState({ selectedUser: params.row, anchorEl: event.currentTarget });
	}
	handleClose = (e) => {
		switch (e) {
			case "activate":
				let user = this.state.selectedUser;
				user.active = true;
				this.usersService.updateUser(user, this.props.accessToken);
				break;
			case "assignroles":
				break;
			case "delete":
				if (this.state.selectedUser.active !== true) {
					this.usersService.deleteUser(this.state.selectedUser.userId, this.props.accessToken);
				}
				else {
					alert("Cannot delete active users");
				}
				break;
			default:
				break;
		}
		this.setState({ anchorEl: null });
	};
	getUsers = (page, pageSize, sortingMode, filterModel) => {
		var f = sortingMode[0];
		this.usersService.retrieveUsers(page, pageSize, f.field, f.sort, filterModel, this.props.accessToken).then(data => {
			if (data != null) {
				if (data.error != null) {
					if (data.error === config.InvalidTokenResp) {
						this.signInService.clearData();
						this.setState({ redirectTo: "signin", redirect: true, loading: false });
					}
					else {
						this.setState({ loading: false });
					}
				}
				else {
					this.setState({ users: data, loading: false });
				}
			}
			else {
				this.setState({ loading: false });
			}
		});
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
			</GridToolbarContainer>
		);
	}
}
export default Users;