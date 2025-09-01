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