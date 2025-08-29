package com.goglotek.frauddetector.datastoreservice.service.impl;

import java.util.List;
import java.util.Optional;

import com.goglotek.frauddetector.datastoreservice.model.Processing;
import com.goglotek.frauddetector.datastoreservice.repository.ProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.datastoreservice.service.ProcessingService;

@Service
public class ProcessingServiceImpl implements ProcessingService {
    @Autowired
    private ProcessingRepository processingRepository;

    @Override
    public List<Processing> findAllPaged(Integer page, Integer limit, String order) {
        return processingRepository.findAll(PageRequest.of(page, limit, Sort.by(order))).getContent();
    }

    @Override
    public Optional<Processing> findById(Long reconId) {
        return processingRepository.findById(reconId);
    }

    @Override
    public Processing save(Processing processing) {
        return processingRepository.save(processing);
    }

}
