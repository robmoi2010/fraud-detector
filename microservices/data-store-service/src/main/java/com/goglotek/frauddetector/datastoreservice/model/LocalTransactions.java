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

@Entity
@Table(name = "local_transactions")
public class LocalTransactions implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 6305631756481483362L;
  @Id
  @Column(name = "transaction_ds_id")
  @SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
  private Long transactionDsId;
  @Column(name = "transactionId", nullable = false)
  private String transactionId;
  @Column(name = "amount", nullable = false)
  private Double amount;
  @Column(name = "clientAccount", nullable = false)
  private String clientAccount;
  @Column(name = "transactiontime", nullable = false)
  private Date transactionTime;
  @Column(name = "created_on", nullable = false)
  private Date createdOn;

  public Long getTransactionDsId() {
    return transactionDsId;
  }

  public void setTransactionDsId(Long transactionDsId) {
    this.transactionDsId = transactionDsId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
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

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }
}
