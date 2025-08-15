package com.goglotek.mpesareconciliation.transactionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFetchedTrans;

@Repository
public interface MpesaFetchedTransRepository extends JpaRepository<MpesaFetchedTrans, Long> {

}
