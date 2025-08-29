import { config } from '../configuration/config';
import RestClient from '../util/RestClient';
class FileService {
    constructor() {
        this.restClient = new RestClient();
    }
    async retrieveFiles(page, pageSize, orderBy, direction, filterObject, accessToken) {
        var obj = {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                "Authorization": "Bearer " + accessToken
            }
        };
        if (filterObject === null) {
            return this.restClient.sendRequest(config.BaseURL + "files/?page=" + page + "&limit=" + pageSize + "&order_by=" + orderBy + "&direction=" + direction + "", obj);
        }
        else {
            return this.restClient.sendRequest(config.BaseURL + "files/filter/?page=" + page + "&limit=" + pageSize + "&order_by=" + orderBy + "&direction=" + direction + "&filter_column=" + filterObject.columnField + "&operator_value=" + filterObject.operatorValue + "&value=" + filterObject.value + "", obj);
        }
    }
}
export default FileService;