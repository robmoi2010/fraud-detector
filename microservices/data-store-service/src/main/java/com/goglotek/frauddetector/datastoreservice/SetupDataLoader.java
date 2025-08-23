package com.goglotek.frauddetector.datastoreservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.goglotek.frauddetector.datastoreservice.model.Permissions;
import com.goglotek.frauddetector.datastoreservice.model.Role;
import com.goglotek.frauddetector.datastoreservice.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userService.findByEmail("user2@gmail.com") == null) {
            Users user = new Users();
            user.setEmail("user2@gmail.com");
            user.setUsername("user2@gmail.com");
            user.setEnabled(true);
            user.setPassword(encoder.encode("password"));
            user.setAccountNotExpired(true);
            user.setAccountNotLocked(true);
            user.setCreatedOn(new Date());
            user.setCredentialsNotExpired(true);
            user.setActive(true);
            userService.create(user);
        }
        if (userService.findByEmail("user1@gmail.com") == null) {
            Users u = new Users();
            u.setEmail("user1@gmail.com");
            u.setUsername("user1@gmail.com");
            u.setEnabled(true);
            u.setAccountNotExpired(true);
            u.setAccountNotLocked(true);
            u.setCreatedOn(new Date());
            u.setCredentialsNotExpired(true);
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
            u.setFirstName("user1");
            u.setLastName("user1");
            u.setCreatedOn(new Date());
            u.setPassword(encoder.encode("password"));
            userService.create(u);
        }
    }
}
