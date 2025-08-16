package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Processing;

import java.util.List;
import java.util.Optional;


public interface ProcessingService {

	List<Processing> findAllPaged(Integer page, Integer limit, String order);

	Optional<Processing> findById(Long reconId);

}