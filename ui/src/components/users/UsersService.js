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
import { config } from '../configuration/config';
import RestClient from '../util/RestClient';
class UsersService {
	constructor() {
		this.restClient = new RestClient();
	}
	async retrieveUsers(page, pageSize, orderBy, direction, filterModel, accessToken) {
		var obj = {
			method: 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				"Authorization": "Bearer " + accessToken
			},
			body: JSON.stringify(filterModel.items)
		};
		return this.restClient.sendRequest(config.BaseURL + "users/filter/?page=" + page + "&limit=" + pageSize + "&order_by=" + orderBy + "&direction=" + direction + "", obj);
	}
	async updateUser(user, accessToken) {
		var obj = {
			method: 'PUT',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				"Authorization": "Bearer " + accessToken
			},
			body: JSON.stringify(user)
		};
		return this.restClient.sendRequest(config.BaseURL + "users/update/" + user.userId, obj);
	}
	async deleteUser(userId, accessToken) {
		var obj = {
			method: 'DELETE',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				"Authorization": "Bearer " + accessToken
			}
		};
		return this.restClient.sendRequest(config.BaseURL + "users/delete/" + userId, obj);
	}
}
export default UsersService;