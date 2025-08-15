package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconTransactions;

@Repository
public interface MpesaReconTransactionsRepository extends JpaRepository<MpesaReconTransactions, Long> {
	@Query(value = "SELECT m FROM MpesaReconTransactions m WHERE comments LIKE %:comments% OR action LIKE %:action%")
	List<MpesaReconTransactions> search(@Param("comments") String comments, @Param("action") String action);

}
