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