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

package com.goglotek.frauddetector.datastoreservice.controller;

import com.goglotek.frauddetector.datastoreservice.dto.CreateUserDto;
import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.dto.UserDto;
import com.goglotek.frauddetector.datastoreservice.exception.UserNotFoundException;
import com.goglotek.frauddetector.datastoreservice.model.Users;
import com.goglotek.frauddetector.datastoreservice.service.UsersService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UsersController {

  @Autowired
  private UsersService userService;

  @RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody Users findByEmail(
      @RequestParam(name = "email", required = true) String email) {
    return userService.findByEmail(email);
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody Users createUser(@RequestBody CreateUserDto userDto) {
    Users user = new Users();
    user.setAccountVerified(userDto.isAccountVerified());
    user.setEmail(userDto.getEmail());
    user.setFailedLoginAttempts(userDto.getFailedLoginAttempts());
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setLoginDisabled(userDto.isLoginDisabled());
    user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
    user.setUsername(userDto.getEmail().split("@")[0]);
    user.setCreatedOn(new Date());
    user.setUpdatedOn(new Date());
    return userService.create(user);
  }

  @PreAuthorize("hasAuthority('ROLE_USER')")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
  public @ResponseBody Users updateUser(@RequestBody CreateUserDto userDto,
      @PathVariable("id") Long userId) {
    Users user = userService.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("No user with ID " + userId));
    user.setAccountVerified(userDto.isAccountVerified());
    user.setFailedLoginAttempts(userDto.getFailedLoginAttempts());
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setLoginDisabled(userDto.isLoginDisabled());
    user.setUpdatedOn(new Date());
    user.setActive(userDto.isActive());
    return userService.create(user);
  }

  @PreAuthorize("hasAuthority('ROLE_USER')")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public void deleteUser(@PathVariable("id") Long userId) {
    userService.deleteById(userId);
  }

  @PreAuthorize("hasAuthority('ROLE_USER')")
  @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody UserDto filteredUsers(
      @RequestParam(name = "page", required = true) Integer page,
      @RequestParam(name = "limit", required = true) Integer limit,
      @RequestParam(name = "order_by", required = true) String order,
      @RequestParam(name = "direction", required = true) String direction,
      @RequestBody List<FilterModel> filterModel) {
    UserDto dto = new UserDto();
    dto.setCount(userService.countAllFilteredPaged(filterModel));
    dto.setUser(userService.findAllFilteredPaged(page, limit, order, direction, filterModel));
    return dto;
  }
}
