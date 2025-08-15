import { config } from '../configuration/config';
import RestClient from '../util/RestClient';
class PaybillsService {
	constructor() {
		this.restClient = new RestClient();
	}
	async retrievePaybills(page, pageSize, orderBy, direction, filterModel, accessToken) {
		var obj = {
			method: 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				"Authorization": "Bearer " + accessToken
			},
			body: JSON.stringify(filterModel.items)
		};
		return this.restClient.sendRequest(config.BaseURL + "paybills/filter/?page=" + page + "&limit=" + pageSize + "&order_by=" + orderBy + "&direction=" + direction + "", obj);
	}
	async retrieveAllPaybills(accessToken) {
		var obj = {
			method: 'GET',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				"Authorization": "Bearer " + accessToken
			}
		};
		return this.restClient.sendRequest(config.BaseURL + "paybills/", obj);

	}
}
export default PaybillsService;
