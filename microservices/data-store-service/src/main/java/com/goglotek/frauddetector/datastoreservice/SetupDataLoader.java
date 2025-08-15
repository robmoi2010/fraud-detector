package com.goglotek.frauddetector.datastoreservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.goglotek.frauddetector.datastoreservice.model.Permissions;
import com.goglotek.frauddetector.datastoreservice.model.Role;
import com.goglotek.frauddetector.datastoreservice.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.goglotek.frauddetector.datastoreservice.service.PermissionsService;
import com.goglotek.frauddetector.datastoreservice.service.RoleService;
import com.goglotek.frauddetector.datastoreservice.service.UsersService;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    UsersService userService;
    @Autowired
    PermissionsService permissionService;
    @Autowired
    RoleService roleService;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userService.findByEmail("robmoi2010@gmail.com") == null) {
            Users u = new Users();
            u.setEmail("robmoi2010@gmail.com");
            Permissions read = new Permissions();
            read.setName("READ");
            Permissions write = new Permissions();
            write.setName("WRITE");
            permissionService.createAll(Arrays.asList(read, write));
            List<Permissions> adminPerm = new ArrayList<Permissions>();
            adminPerm.add(write);
            adminPerm.add(read);
            List<Permissions> userPerm = new ArrayList<Permissions>();
            userPerm.add(read);
            List<Role> roles = new ArrayList<Role>();
            Role admin = new Role();
            admin.setName("ROLE_SUPER_ADMIN");
            admin.setPermissions(adminPerm);
            roles.add(admin);
            Role user = new Role();
            user.setName("ROLE_USER");
            user.setPermissions(adminPerm);
            roles.add(user);
            roleService.createAll(roles);
            u.setRoles(roles);
            u.setUsername("robmoi");
            u.setFirstName("Robert");
            u.setLastName("Moi");
            u.setCreatedOn(new Date());
            u.setPassword(new BCryptPasswordEncoder().encode("password"));
            userService.create(u);
        }
    }
}
