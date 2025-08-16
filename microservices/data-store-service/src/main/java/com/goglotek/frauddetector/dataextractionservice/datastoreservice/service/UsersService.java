package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Users;

public interface UsersService {

	Users findByEmail(String email);

	Users create(Users user);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<Users> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
                                     List<FilterModel> filterModel);

	Optional<Users> findById(Long userId);

	void deleteById(long userId);

}