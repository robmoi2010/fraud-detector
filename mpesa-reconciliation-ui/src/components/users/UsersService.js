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