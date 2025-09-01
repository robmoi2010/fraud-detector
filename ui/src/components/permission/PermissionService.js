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
class PermissionService {
	hasRole = (user, role, exact) => {
		if (user === null || user === undefined) {
			return false;
		}
		let roles = user.roles;
		if (roles === null || roles === undefined) {
			return false;
		}
		for (let i = 0; i < roles.length; i++) {
			if (exact) {
				if (roles[i].name === role) {
					return true;
				}
			}
			else {
				if (roles[i].name.includes(role)) {
					return true;
				}
			}
		}
		return false;
	}
	hasPermission = (user, permission) => {
		if (user === null || user === undefined) {
			return false;
		}
		let roles = user.roles;
		if (roles === null || roles === undefined) {
			return false;
		}
		for (let i = 0; i < roles.length; i++) {
			let r = roles[i];
			for (let j = 0; j < r.permission.length; j++) {
				if (r.permission[j] === permission) {
					return true;
				}
			}
		}
		return false;
	}
}
export default PermissionService;