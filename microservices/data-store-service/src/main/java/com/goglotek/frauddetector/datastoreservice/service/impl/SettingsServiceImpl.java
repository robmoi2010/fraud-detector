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

import com.goglotek.frauddetector.datastoreservice.model.Settings;
import com.goglotek.frauddetector.datastoreservice.repository.SettingsRepository;
import com.goglotek.frauddetector.datastoreservice.service.SettingsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {

  @Autowired
  private SettingsRepository settingsRepository;

  @Override
  public List<Settings> list() {
    return settingsRepository.findAll();
  }
}
