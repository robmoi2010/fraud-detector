
class RestClient {
	async getAccessToken(URL, obj) {
		return fetch(URL, obj)
			.then(response => {
				return response.json();
			})
			.catch((error) => {
				console.error('Error:', error);
			});
	}
	async sendRequest(URL, param) {
		return fetch(URL, param)
			.then(response => response.json())
			.then(data => {
				return data;
			})
			.catch((error) => {
				console.error('Error:', error);
			});
	}
}
export default RestClient;