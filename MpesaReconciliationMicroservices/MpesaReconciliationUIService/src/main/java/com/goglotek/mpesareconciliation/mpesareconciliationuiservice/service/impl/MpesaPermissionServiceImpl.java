package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaPermission;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository.MpesaPermissionRepository;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaPermissionService;


@Service
public class MpesaPermissionServiceImpl implements MpesaPermissionService {
	@Autowired
	MpesaPermissionRepository mpesaPermissionRepository;

	@Override
	public List<MpesaPermission> createAll(List<MpesaPermission> perms) {
		return mpesaPermissionRepository.saveAll(perms);
	}
}
