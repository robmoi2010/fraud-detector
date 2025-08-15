import { config } from '../configuration/config';
import RestClient from '../util/RestClient';
class FileTransactionsService {
    constructor() {
        this.restClient = new RestClient();
    }
    async retrieveFileTransactions(page, pageSize, orderBy, direction, filterModel, accessToken) {
        var obj = {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + accessToken
            },
            body: JSON.stringify(filterModel.items)
        };
        return this.restClient.sendRequest(config.BaseURL + "transactions/filter/?page=" + page + "&limit=" + pageSize + "&order_by=" + orderBy + "&direction=" + direction + "", obj);
    }
}
export default FileTransactionsService;
