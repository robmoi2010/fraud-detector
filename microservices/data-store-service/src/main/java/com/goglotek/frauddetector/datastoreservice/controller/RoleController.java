package com.goglotek.frauddetector.datastoreservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.dto.RoleDto;
import com.goglotek.frauddetector.datastoreservice.service.RoleService;

@RequestMapping("roles")
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RoleDto filteredRoles(@RequestParam(name = "page", required = true) Integer page,
                                               @RequestParam(name = "limit", required = true) Integer limit,
                                               @RequestParam(name = "order_by", required = true) String order,
                                               @RequestParam(name = "direction", required = true) String direction,
                                               @RequestBody List<FilterModel> filterModel) {
		RoleDto dto = new RoleDto();
		dto.setCount(roleService.countAllFilteredPaged(filterModel));
		dto.setRoles(roleService.findAllFilteredPaged(page, limit, order, direction, filterModel));
		return dto;
	}
}
