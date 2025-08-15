package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFetchedTrans;

@Repository
public interface MpesaFetchedTransRepository extends JpaRepository<MpesaFetchedTrans, Long> {
	@Query(value = "SELECT m FROM MpesaFetchedTrans m WHERE LOWER(receiptNo) LIKE %:receipt% OR LOWER(details) LIKE %:details% OR LOWER(transactionStatus) LIKE %:transactionStatus% OR LOWER(reasonType) LIKE %:reasonType% OR LOWER(otherPartyInfo) LIKE %:otherPInfo% OR LOWER(msisdn) LIKE %:msisdn% OR LOWER(shortcode) LIKE %:shortcode% OR LOWER(firstname) LIKE %:firstname% OR LOWER(middlename) LIKE %:middlename% OR LOWER(lastname) LIKE %:lastname%")
	public List<MpesaFetchedTrans> search(@Param("receipt") String receipt, @Param("details") String details,
			@Param("transactionStatus") String transStatus, @Param("reasonType") String reasonType,
			@Param("otherPInfo") String otherPInfo, @Param("msisdn") String msisdn,
			@Param("shortcode") String shortcode, @Param("firstname") String firstname,
			@Param("middlename") String middlename, @Param("lastname") String lastname);
}
