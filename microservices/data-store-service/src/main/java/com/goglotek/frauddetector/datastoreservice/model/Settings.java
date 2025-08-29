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
@Table(name = "settings")
public class Settings implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -5703995793557235364L;
  @Id
  @Column(name = "settings_id")
  @SequenceGenerator(name = "settings_sequence", sequenceName = "settings_sq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settings_sequence")
  private long settingsId;
  @Column(name = "created_date", nullable = false)
  private Date createdDate;
  @Column(name = "modified_date", nullable = false)
  private Date modifiedDate;
  @Column(name = "active", nullable = false)
  private boolean active;
  @Column(name = "minimum_processing_date", nullable = false)
  private Date minimumProcessingDate;

  public long getSettingsId() {
    return settingsId;
  }

  public void setSettingsId(long settingsId) {
    this.settingsId = settingsId;
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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Date getMinimumProcessingDate() {
    return minimumProcessingDate;
  }

  public void setMinimumProcessingDate(Date minimumProcessingDate) {
    this.minimumProcessingDate = minimumProcessingDate;
  }
}
