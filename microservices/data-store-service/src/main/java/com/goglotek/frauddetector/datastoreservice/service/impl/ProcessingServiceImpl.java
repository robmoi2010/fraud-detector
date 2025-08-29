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

import com.goglotek.frauddetector.datastoreservice.model.Processing;
import com.goglotek.frauddetector.datastoreservice.repository.ProcessingRepository;
import com.goglotek.frauddetector.datastoreservice.service.ProcessingService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
