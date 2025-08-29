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

package com.goglotek.frauddetector.datastoreservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "provider_transactions")
@Validated
public class ProviderTransactions implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 2457593276297588753L;
  @Id
  @SequenceGenerator(name = "provider_transactions_sequence", sequenceName = "provider_transactions_sq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_transactions_sequence")
  @Column(name = "provider_transaction_id")
  private Long transactionDsId;

  @Column(name = "transaction_id", nullable = false, unique = true)
  private String transactionId;

  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  @Column(name = "modified_date", nullable = false)
  private Date modifiedDate;

  @Column(name = "client_account", nullable = false)
  private String clientAccount;

  @Column(name = "transaction_time", nullable = false)
  private Date transactionTime;

  //tune length depending on the size of your transactions extra details
  @Column(name = "details", nullable = false, length = 10000)
  private String details;

  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "file_id", nullable = false)
  private Long fileId;

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public String getClientAccount() {
    return clientAccount;
  }

  public void setClientAccount(String clientAccount) {
    this.clientAccount = clientAccount;
  }

  public Date getTransactionTime() {
    return transactionTime;
  }

  public void setTransactionTime(Date transactionTime) {
    this.transactionTime = transactionTime;
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

  public Long getFileId() {
    return fileId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }

  public Long getTransactionDsId() {
    return transactionDsId;
  }

  public void setTransactionDsId(Long transactionDsId) {
    this.transactionDsId = transactionDsId;
  }
}