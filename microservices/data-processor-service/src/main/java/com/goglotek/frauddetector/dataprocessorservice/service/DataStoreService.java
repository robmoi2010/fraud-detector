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

package com.goglotek.frauddetector.dataprocessorservice.service;

import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.Transaction;
import java.util.List;

public interface DataStoreService {

  List<Transaction> getProviderTransactions(FileDto file) throws Exception;

  List<Transaction> getLocalTransactions(FileDto file) throws Exception;

  void storeProcessedTransactionData(ProcessedTransactionDto processed) throws Exception;
}
