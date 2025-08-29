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

package com.goglotek.frauddetector.dataprocessorservice.dto;

import java.util.Date;
import java.util.List;

public class ProcessedTransactionDto {

  private String fileGlobalId;
  private Date dateProcessed;
  private Date from;
  private Date to;
  private List<Transaction> fraudulentTransactions;
  private List<Transaction> missingTransactions;
  private List<Transaction> invalidAmountTransactions;
  private List<Transaction> invalidClientAccountTransactions;
  private List<Transaction> invalidTimestampTransactions;

  public List<Transaction> getInvalidClientAccountTransactions() {
    return invalidClientAccountTransactions;
  }

  public void setInvalidClientAccountTransactions(
      List<Transaction> invalidClientAccountTransactions) {
    this.invalidClientAccountTransactions = invalidClientAccountTransactions;
  }

  public List<Transaction> getInvalidTimestampTransactions() {
    return invalidTimestampTransactions;
  }

  public void setInvalidTimestampTransactions(List<Transaction> invalidTimestampTransactions) {
    this.invalidTimestampTransactions = invalidTimestampTransactions;
  }

  public List<Transaction> getInvalidAmountTransactions() {
    return invalidAmountTransactions;
  }

  public void setInvalidAmountTransactions(List<Transaction> invalidAmountTransactions) {
    this.invalidAmountTransactions = invalidAmountTransactions;
  }

  public Date getDateProcessed() {
    return dateProcessed;
  }

  public void setDateProcessed(Date dateProcessed) {
    this.dateProcessed = dateProcessed;
  }

  public List<Transaction> getFraudulentTransactions() {
    return fraudulentTransactions;
  }

  public void setFraudulentTransactions(List<Transaction> fraudulentTransactions) {
    this.fraudulentTransactions = fraudulentTransactions;
  }

  public List<Transaction> getMissingTransactions() {
    return missingTransactions;
  }

  public void setMissingTransactions(List<Transaction> missingTransactions) {
    this.missingTransactions = missingTransactions;
  }

  public String getFileGlobalId() {
    return fileGlobalId;
  }

  public void setFileGlobalId(String fileGlobalId) {
    this.fileGlobalId = fileGlobalId;
  }

  public Date getFrom() {
    return from;
  }

  public void setFrom(Date from) {
    this.from = from;
  }

  public Date getTo() {
    return to;
  }

  public void setTo(Date to) {
    this.to = to;
  }
}
