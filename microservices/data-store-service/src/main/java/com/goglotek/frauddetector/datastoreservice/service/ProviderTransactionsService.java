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

package com.goglotek.frauddetector.datastoreservice.service;

import com.goglotek.frauddetector.datastoreservice.dto.CreateProviderTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.model.ProviderTransactions;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProviderTransactionsService {

  List<ProviderTransactions> list();

  List<ProviderTransactions> findAllPaged(Integer page, Integer limit, String order,
      String direction);

  Optional<ProviderTransactions> findById(Long transId);

  List<ProviderTransactions> search(String lowerCase);

  long countAll();

  List<ProviderTransactions> findAllFilteredPaged(Integer page, Integer limit, String order,
      String direction,
      List<FilterModel> filterModel);

  long countAllFilteredPaged(List<FilterModel> filterModel);

  @Transactional
  List<ProviderTransactions> createAll(List<CreateProviderTransactionsDto> transactionsDtoList,
      Files file) throws GoglotekException;

  public List<ProviderTransactions> findByFileGlobalId(String fileGlobalId)
      throws GoglotekException;
}