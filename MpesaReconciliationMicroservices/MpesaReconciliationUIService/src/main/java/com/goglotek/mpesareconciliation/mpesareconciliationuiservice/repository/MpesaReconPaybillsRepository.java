package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconPaybills;

@Repository
public interface MpesaReconPaybillsRepository extends JpaRepository<MpesaReconPaybills, Long> {
	MpesaReconPaybills findByPaybill(String paybill);
}
