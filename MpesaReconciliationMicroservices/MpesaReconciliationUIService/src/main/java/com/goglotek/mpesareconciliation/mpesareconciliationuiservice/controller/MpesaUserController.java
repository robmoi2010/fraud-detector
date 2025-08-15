package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.controller;

import java.util.Date;
import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.exception.UserNotFoundException;
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

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.CreateUserDto;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.MpesaUserDto;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaUser;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaUserService;

@RestController
@RequestMapping("users")
public class MpesaUserController {
    @Autowired
    private MpesaUserService mpesaUserService;

    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody MpesaUser findByEmail(@RequestParam(name = "email", required = true) String email) {
        return mpesaUserService.findByEmail(email);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody MpesaUser createUser(@RequestBody CreateUserDto userDto) {
        MpesaUser user = new MpesaUser();
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
        return mpesaUserService.create(user);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody MpesaUser updateUser(@RequestBody CreateUserDto userDto, @PathVariable("id") Long userId) {
        MpesaUser user = mpesaUserService.findById(userId).orElseThrow(() -> new UserNotFoundException("No user with ID " + userId));
        user.setAccountVerified(userDto.isAccountVerified());
        user.setFailedLoginAttempts(userDto.getFailedLoginAttempts());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setLoginDisabled(userDto.isLoginDisabled());
        user.setUpdatedOn(new Date());
        user.setActive(userDto.isActive());
        return mpesaUserService.create(user);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Long userId) {
        mpesaUserService.deleteById(userId);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody MpesaUserDto filteredUsers(@RequestParam(name = "page", required = true) Integer page,
                                                    @RequestParam(name = "limit", required = true) Integer limit,
                                                    @RequestParam(name = "order_by", required = true) String order,
                                                    @RequestParam(name = "direction", required = true) String direction,
                                                    @RequestBody List<FilterModel> filterModel) {
        MpesaUserDto dto = new MpesaUserDto();
        dto.setCount(mpesaUserService.countAllFilteredPaged(filterModel));
        dto.setMpesaUser(mpesaUserService.findAllFilteredPaged(page, limit, order, direction, filterModel));
        return dto;
    }
}
