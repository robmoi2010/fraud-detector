package com.goglotek.mpesareconciliation.transactionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.transactionservice.model.MpesaReconPaybills;

@Repository
public interface MpesaReconPaybillsRepository extends JpaRepository<MpesaReconPaybills, Long> {
	MpesaReconPaybills findByPaybill(String paybill);
}
