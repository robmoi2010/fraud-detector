import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import Autocomplete from '@material-ui/lab/Autocomplete';
import CircularProgress from '@material-ui/core/CircularProgress';
import DialogTitle from '@material-ui/core/DialogTitle';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import PaybillsService from '../paybill/PaybillsService';
export default function AddRoleForm(props) {
	const handleClose = () => {
		props.handleCloseForm();
	};
	const useStyles = makeStyles((theme) => ({
		paper: {
			marginTop: theme.spacing(8),
			display: 'flex',
			flexDirection: 'column',
			alignItems: 'center',
		},
		avatar: {
			margin: theme.spacing(1),
			backgroundColor: theme.palette.secondary.main,
		},
		form: {
			width: '100%', // Fix IE 11 issue.
			marginTop: theme.spacing(3),
		},
		submit: {
			margin: theme.spacing(3, 0, 2),
		},
	}));
	const handleSubmit = (e) => {

	}
	const classes = useStyles();
	let open = false;
	let loading = false;
	const paybillsService = new PaybillsService();
	const paybills = async () => { return await paybillsService.retrieveAllPaybills(props.accessToken); }
	console.log(paybills);
	return (
		<Dialog open={props.formOpen} onClose={handleClose} aria-labelledby="form-dialog-title">
			<DialogTitle id="form-dialog-title">Add Role</DialogTitle>
			<DialogContent>
				<form method="POST" className={classes.form} onSubmit={(e) => handleSubmit(e)} Validate>
					<Grid container spacing={2}>
						<Grid item xs={12}>
							<Autocomplete
								id="paybills"
								options={paybills}
								getOptionLabel={(option) => option.paybill}
								style={{ width: 300 }}
								renderInput={(params) => <TextField {...params} label="Combo box" variant="outlined" />}
							/>
						</Grid>
						<Grid item xs={12}>
							<TextField
								type="password"
								variant="outlined"
								required
								fullWidth
								name="password"
								label="Password"
								id="password"
								autoComplete="current-password"
							/>
						</Grid>
						<Grid item xs={12}>
							<TextField
								variant="outlined"
								required
								fullWidth
								name="confirmpassword"
								label="Confirm Password"
								type="password"
								id="confirmpassword"
								autoComplete="confirm-password"
							/>
						</Grid>
					</Grid>
					<Button
						type="submit"
						fullWidth
						variant="contained"
						color="primary"
						className={classes.submit}
					>
						Save
                      </Button>
				</form>
			</DialogContent>
			<DialogActions>
				<Button onClick={handleClose} color="primary">
					Cancel
          		</Button>
				{/* <Button onClick={handleClose} color="primary">
					Save
          			</Button> */}
			</DialogActions>
		</Dialog>
	);
}