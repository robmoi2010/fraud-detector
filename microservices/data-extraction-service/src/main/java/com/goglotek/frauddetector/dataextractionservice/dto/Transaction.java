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

package com.goglotek.frauddetector.dataextractionservice.dto;

import java.util.Date;

public class Transaction {

  private Date createdOn;
  private String id;
  private String details;
  private Double amount;
  private Date transactionTimestamp;
  private String groupAccount;
  private String clientAccount;

  public String getClientAccount() {
    return clientAccount;
  }

  public void setClientAccount(String clientAccount) {
    this.clientAccount = clientAccount;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Date getTransactionTimestamp() {
    return transactionTimestamp;
  }

  public String getGroupAccount() {
    return groupAccount;
  }

  public void setGroupAccount(String groupAccount) {
    this.groupAccount = groupAccount;
  }

  public void setTransactionTimestamp(Date transactionTimestamp) {
    this.transactionTimestamp = transactionTimestamp;
  }
}
