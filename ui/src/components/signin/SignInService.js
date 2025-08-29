import base64 from 'base-64';
import { config } from '../configuration/config';
import RestClient from '../util/RestClient';
class SignInService {
	constructor() {
		this.restClient = new RestClient();
	}
	getToken() {
		const tokenString = localStorage.getItem(config.TokeName);
		return JSON.parse(tokenString);
	}
	async setData(userToken) {
		localStorage.setItem(config.TokeName, JSON.stringify(userToken));
	}
	async clearData() {
		localStorage.removeItem(config.TokeName);
	}
	async getAccessToken(username, password) {
		var encoded = "Basic " + base64.encode(config.OauthId + ":" + config.OauthSecret);
		var obj = {
			method: 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/x-www-form-urlencoded',
				"Authorization": encoded
			},
			body:
				"grant_type=password&username=" + username + "&password=" + password + ""
		};
		var accessToken = this.restClient.getAccessToken(config.BaseURL + "oauth/token", obj);
		return accessToken;
	}
	async getUserDetails(username, accessToken) {
		var obj = {
			method: 'GET',
			headers: {
				'Accept': 'application/json',
				"Authorization": "Bearer " + accessToken
			}
		};
		return this.restClient.sendRequest(config.BaseURL + "users/find/?email=" + username + "", obj);
	}
}
export default SignInService;