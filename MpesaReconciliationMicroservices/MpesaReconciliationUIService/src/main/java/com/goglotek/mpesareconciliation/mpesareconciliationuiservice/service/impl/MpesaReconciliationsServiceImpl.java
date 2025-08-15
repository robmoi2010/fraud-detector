package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconciliations;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository.MpesaReconciliationsRepository;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaReconciliationsService;

@Service
public class MpesaReconciliationsServiceImpl implements MpesaReconciliationsService {
	@Autowired
	private MpesaReconciliationsRepository mpesaReconciliationsRepository;

	@Override
	public List<MpesaReconciliations> findAllPaged(Integer page, Integer limit, String order) {
		return mpesaReconciliationsRepository.findAll(PageRequest.of(page, limit, Sort.by(order))).getContent();
	}

	@Override
	public Optional<MpesaReconciliations> findById(Long reconId) {
		return mpesaReconciliationsRepository.findById(reconId);
	}

}
