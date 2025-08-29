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
