package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.impl;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Settings;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.SettingsService;

@Service
public class SettingsServiceImpl implements SettingsService {
	@Autowired
	private SettingsRepository settingsRepository;

	@Override
	public List<Settings> list() {
		return settingsRepository.findAll();
	}
}
