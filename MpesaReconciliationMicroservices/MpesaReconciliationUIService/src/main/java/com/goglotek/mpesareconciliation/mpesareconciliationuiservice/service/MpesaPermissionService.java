package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaPermission;

public interface MpesaPermissionService {

	List<MpesaPermission> createAll(List<MpesaPermission> perms);

}