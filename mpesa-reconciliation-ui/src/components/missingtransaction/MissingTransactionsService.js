import RestClient from '../util/RestClient';
import { config } from '../configuration/config';
class MissingTransactionsService {
	constructor() {
		this.restClient = new RestClient();
	}
	async retrieveMissingTransactions(page, pageSize, orderBy, direction, filterModel, accessToken) {
		var obj = {
			method: 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				"Authorization": "Bearer " + accessToken
			},
			body: JSON.stringify(filterModel.items)
		};
		return this.restClient.sendRequest(config.BaseURL + "reconciledtransactions/filter/?page=" + page + "&limit=" + pageSize + "&order_by=" + orderBy + "&direction=" + direction + "", obj);
	}
}
export default MissingTransactionsService;