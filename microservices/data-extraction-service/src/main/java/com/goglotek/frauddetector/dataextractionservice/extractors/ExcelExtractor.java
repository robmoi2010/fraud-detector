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

package com.goglotek.frauddetector.dataextractionservice.extractors;

import com.goglotek.frauddetector.dataextractionservice.dto.Transaction;
import com.goglotek.frauddetector.dataextractionservice.schema.Schema;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

//TODO: Implement for paying clients
@Component
public class ExcelExtractor implements DataExtractor {

  @Override
  public List<Transaction> extractTransactions(byte[] data, Schema schema) throws IOException {
    return Collections.emptyList();
  }

  @Override
  public Date getFromDate() {
    return null;
  }

  @Override
  public Date getToDate() {
    return null;
  }
}
