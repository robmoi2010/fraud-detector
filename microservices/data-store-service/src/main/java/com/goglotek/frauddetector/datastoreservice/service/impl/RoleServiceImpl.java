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

package com.goglotek.frauddetector.datastoreservice.service.impl;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.model.Role;
import com.goglotek.frauddetector.datastoreservice.repository.RoleRepository;
import com.goglotek.frauddetector.datastoreservice.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public List<Role> createAll(List<Role> roles) {
    return roleRepository.saveAll(roles);
  }

  private Role createFilterObject(List<FilterModel> filterModel) {
    Role role = new Role();
    for (FilterModel m : filterModel) {
      String filterColumn = m.getColumnField();
      String value = m.getValue();
      switch (filterColumn.toLowerCase()) {
        case "name":
          role.setName(value);
          break;
      }
    }
    return role;
  }

  private ExampleMatcher createFilterMatcher(List<FilterModel> filterModel) {
    ExampleMatcher matcher = ExampleMatcher.matchingAll();
    for (FilterModel m : filterModel) {
      switch (m.getOperatorValue().toLowerCase()) {
        case "contains": {
          matcher = matcher
              .withMatcher(m.getColumnField(),
                  ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
              .withIgnoreNullValues();
          break;
        }
        case "equals": {
          matcher = matcher
              .withMatcher(m.getColumnField(),
                  ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
              .withIgnoreNullValues();
          break;
        }
        case "startswith": {
          matcher = matcher
              .withMatcher(m.getColumnField(),
                  ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
              .withIgnoreNullValues();
          break;
        }
        case "endswith": {
          matcher = matcher
              .withMatcher(m.getColumnField(),
                  ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase())
              .withIgnoreNullValues();
          break;
        }
        default:
          matcher = matcher
              .withMatcher(m.getColumnField(),
                  ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
              .withIgnoreNullValues();
          break;
      }
    }
    matcher = matcher.withIgnoreNullValues();
    return matcher;
  }

  @Override
  public long countAllFilteredPaged(List<FilterModel> filterModel) {
    Example<Role> example = Example.of(createFilterObject(filterModel),
        createFilterMatcher(filterModel));
    return roleRepository.count(example);
  }

  @Override
  public List<Role> findAllFilteredPaged(Integer page, Integer limit, String order,
      String direction,
      List<FilterModel> filterModel) {
    Example<Role> example = Example.of(createFilterObject(filterModel),
        createFilterMatcher(filterModel));
    return roleRepository
        .findAll(example, PageRequest.of(page, limit,
            direction.equalsIgnoreCase("desc") ? Sort.by(order).descending()
                : Sort.by(order).ascending()))
        .getContent();
  }

}
