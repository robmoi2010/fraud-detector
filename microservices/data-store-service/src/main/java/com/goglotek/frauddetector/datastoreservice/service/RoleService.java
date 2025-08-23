package com.goglotek.frauddetector.datastoreservice.service;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.model.Role;

public interface RoleService {

	List<Role> createAll(List<Role> roles);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<Role> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

}