package com.goglotek.frauddetector.datastoreservice.service;

import com.goglotek.frauddetector.datastoreservice.model.Processing;

import java.util.List;
import java.util.Optional;


public interface ProcessingService {

	List<Processing> findAllPaged(Integer page, Integer limit, String order);

	Optional<Processing> findById(Long reconId);

}