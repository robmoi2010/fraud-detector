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
import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";

class AlertDialog extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			open: this.props.open
		};
	}
	handleClose = () => {
		this.setState({ open: false });
	};

	handleYes = () => {
		console.log("I agree!");
		this.handleClose();
	};
	handleNo = () => {
		console.log("I do not agree.");
		this.handleClose();
	};
	render() {
		return (
			<div>
				<Dialog
					open={this.state.open}
					onClose={this.handleClose}
					aria-labelledby="alert-dialog-title"
					aria-describedby="alert-dialog-description"
				>
					<DialogTitle id="alert-dialog-title">
						{"dialog"}
					</DialogTitle>
					<DialogContent>
						<DialogContentText id="alert-dialog-description">
							{this.props.message}
						</DialogContentText>
					</DialogContent>
					<DialogActions>
						<Button onClick={this.handleNo} color="primary">
							No
                        </Button>
						<Button onClick={this.handleYes} color="primary" autoFocus>
							Yes
                        </Button>
					</DialogActions>
				</Dialog>
			</div>
		);
	}
}

export default AlertDialog;
