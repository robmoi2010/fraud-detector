package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaMainTransactions;

@Repository
public interface MpesaMainTransactionsRepository extends JpaRepository<MpesaMainTransactions, Long> {

}
