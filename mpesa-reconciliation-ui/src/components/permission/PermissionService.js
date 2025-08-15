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