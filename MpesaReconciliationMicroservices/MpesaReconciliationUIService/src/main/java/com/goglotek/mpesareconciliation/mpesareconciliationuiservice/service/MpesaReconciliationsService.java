package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconciliations;

public interface MpesaReconciliationsService {

	List<MpesaReconciliations> findAllPaged(Integer page, Integer limit, String order);

	Optional<MpesaReconciliations> findById(Long reconId);

}