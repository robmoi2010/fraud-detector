/*
 *
 *  * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  *
 *  * This file is part of the Fraud Detector System.
 *  *
 *  * The Fraud Detector System is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * The Fraud Detector System is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.goglotek.frauddetector.datastoreservice;

import com.goglotek.frauddetector.datastoreservice.model.Permissions;
import com.goglotek.frauddetector.datastoreservice.model.Role;
import com.goglotek.frauddetector.datastoreservice.model.Users;
import com.goglotek.frauddetector.datastoreservice.service.PermissionsService;
import com.goglotek.frauddetector.datastoreservice.service.RoleService;
import com.goglotek.frauddetector.datastoreservice.service.UsersService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

      Permissions delete = new Permissions();
      write.setName("DELETE");

      Permissions update = new Permissions();
      write.setName("UPDATE");

      permissionService.createAll(Arrays.asList(read, write, delete, update));

      List<Permissions> adminPerm = new ArrayList<Permissions>();
      adminPerm.add(write);
      adminPerm.add(read);
      adminPerm.add(delete);
      adminPerm.add(update);

      List<Permissions> userPerm = new ArrayList<Permissions>();
      userPerm.add(read);

      List<Role> roles = new ArrayList<Role>();

      Role adminRole = new Role();
      adminRole.setName("ROLE_SUPER_ADMIN");
      adminRole.setPermissions(adminPerm);
      roles.add(adminRole);

      Role userRole = new Role();
      userRole.setName("ROLE_USER");
      userRole.setPermissions(userPerm);
      roles.add(userRole);

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
