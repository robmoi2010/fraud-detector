package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFetchSettings;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository.MpesaFetchSettingsRepository;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaFetchSettingsService;

@Service
public class MpesaFetchSettingsServiceImpl implements MpesaFetchSettingsService {
	@Autowired
	private MpesaFetchSettingsRepository mpesaFetchSettingsRepository;

	@Override
	public List<MpesaFetchSettings> list() {
		return mpesaFetchSettingsRepository.findAll();
	}
}
