package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFetchSettings;

@Repository
public interface MpesaFetchSettingsRepository extends JpaRepository<MpesaFetchSettings, Long> {
	

}
