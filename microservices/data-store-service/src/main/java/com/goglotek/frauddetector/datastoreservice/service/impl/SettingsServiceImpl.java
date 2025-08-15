package com.goglotek.frauddetector.datastoreservice.service.impl;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.model.Settings;
import com.goglotek.frauddetector.datastoreservice.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.datastoreservice.service.SettingsService;

@Service
public class SettingsServiceImpl implements SettingsService {
	@Autowired
	private SettingsRepository settingsRepository;

	@Override
	public List<Settings> list() {
		return settingsRepository.findAll();
	}
}
