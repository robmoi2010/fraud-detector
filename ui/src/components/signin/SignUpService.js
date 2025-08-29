import base64 from 'base-64';
import { config } from '../configuration/config';
import RestClient from '../util/RestClient';
class SignUpService {
	constructor() {
		this.restClient = new RestClient();
	}

	async getUserDetails(username) {
		var obj = {
			method: 'GET',
			headers: {
				'Accept': 'application/json'
			}
		};
		return this.restClient.sendRequest(config.BaseURL + "users/find/?email=" + username + "", obj);
	}
	async sendUserDetails(user) {
		var obj = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(user),
		};
		return this.restClient.sendRequest(config.BaseURL + "users/create", obj);

	}
}
export default SignUpService;