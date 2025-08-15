package com.goglotek.mpesareconciliation.mpesareconciliationuiservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaPermission;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaRole;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaUser;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaPermissionService;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaRoleService;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaUserService;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	MpesaUserService userService;
	@Autowired
	MpesaPermissionService permissionService;
	@Autowired
	MpesaRoleService roleService;

	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (userService.findByEmail("john.doe@gmail.com") == null) {
			MpesaUser u = new MpesaUser();
			u.setEmail("john.doe@gmail.com");
			MpesaPermission read = new MpesaPermission();
			read.setName("READ");
			MpesaPermission write = new MpesaPermission();
			write.setName("WRITE");
			permissionService.createAll(Arrays.asList(read, write));
			List<MpesaPermission> adminPerm = new ArrayList<MpesaPermission>();
			adminPerm.add(write);
			adminPerm.add(read);
			List<MpesaPermission> userPerm = new ArrayList<MpesaPermission>();
			userPerm.add(read);
			List<MpesaRole> roles = new ArrayList<MpesaRole>();
			MpesaRole admin = new MpesaRole();
			admin.setName("ROLE_SUPER_ADMIN");
			admin.setPermissions(adminPerm);
			roles.add(admin);
			MpesaRole user = new MpesaRole();
			user.setName("ROLE_USER");
			user.setPermissions(adminPerm);
			roles.add(user);
			roleService.createAll(roles);
			u.setRoles(roles);
			u.setUsername("johndoe");
			u.setFirstName("john");
			u.setLastName("doe");
			u.setCreatedOn(new Date());
			u.setPassword(new BCryptPasswordEncoder().encode("password"));
			userService.create(u);
		}
	}
}
