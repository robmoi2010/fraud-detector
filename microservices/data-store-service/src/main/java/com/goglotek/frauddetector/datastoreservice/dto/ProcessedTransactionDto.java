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

package com.goglotek.frauddetector.datastoreservice.dto;

import java.util.Date;
import java.util.List;

public class ProcessedTransactionDto {

  private String fileGlobalId;
  private Date dateProcessed;
  private Date from;
  private Date to;
  private List<ProcessedTransaction> fraudulentTransactions;
  private List<ProcessedTransaction> missingTransactions;
  private List<ProcessedTransaction> invalidAmountTransactions;
  private List<ProcessedTransaction> invalidClientAccountTransactions;
  private List<ProcessedTransaction> invalidTimestampTransactions;

  public List<ProcessedTransaction> getInvalidClientAccountTransactions() {
    return invalidClientAccountTransactions;
  }

  public void setInvalidClientAccountTransactions(
      List<ProcessedTransaction> invalidClientAccountTransactions) {
    this.invalidClientAccountTransactions = invalidClientAccountTransactions;
  }

  public List<ProcessedTransaction> getInvalidTimestampTransactions() {
    return invalidTimestampTransactions;
  }

  public void setInvalidTimestampTransactions(
      List<ProcessedTransaction> invalidTimestampTransactions) {
    this.invalidTimestampTransactions = invalidTimestampTransactions;
  }

  public List<ProcessedTransaction> getInvalidAmountTransactions() {
    return invalidAmountTransactions;
  }

  public void setInvalidAmountTransactions(List<ProcessedTransaction> invalidAmountTransactions) {
    this.invalidAmountTransactions = invalidAmountTransactions;
  }

  public Date getDateProcessed() {
    return dateProcessed;
  }

  public void setDateProcessed(Date dateProcessed) {
    this.dateProcessed = dateProcessed;
  }

  public List<ProcessedTransaction> getFraudulentTransactions() {
    return fraudulentTransactions;
  }

  public void setFraudulentTransactions(List<ProcessedTransaction> fraudulentTransactions) {
    this.fraudulentTransactions = fraudulentTransactions;
  }

  public List<ProcessedTransaction> getMissingTransactions() {
    return missingTransactions;
  }

  public void setMissingTransactions(List<ProcessedTransaction> missingTransactions) {
    this.missingTransactions = missingTransactions;
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

  public String getFileGlobalId() {
    return fileGlobalId;
  }

  public void setFileGlobalId(String fileGlobalId) {
    this.fileGlobalId = fileGlobalId;
  }
}
