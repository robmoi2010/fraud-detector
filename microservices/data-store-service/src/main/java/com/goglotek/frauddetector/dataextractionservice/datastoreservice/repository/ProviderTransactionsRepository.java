package com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.ProviderTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProviderTransactionsRepository extends JpaRepository<ProviderTransactions, Long> {
    @Query(value = "SELECT m FROM ProviderTransactions m WHERE LOWER(receiptNo) LIKE %:receipt% OR LOWER(details) LIKE %:details% OR LOWER(transactionStatus) LIKE %:transactionStatus% OR LOWER(reasonType) LIKE %:reasonType% OR LOWER(otherPartyInfo) LIKE %:otherPInfo% OR LOWER(msisdn) LIKE %:msisdn% OR LOWER(shortcode) LIKE %:shortcode% OR LOWER(firstname) LIKE %:firstname% OR LOWER(middlename) LIKE %:middlename% OR LOWER(lastname) LIKE %:lastname%")
    public List<ProviderTransactions> search(@Param("receipt") String receipt, @Param("details") String details,
                                             @Param("transactionStatus") String transStatus, @Param("reasonType") String reasonType,
                                             @Param("otherPInfo") String otherPInfo, @Param("msisdn") String msisdn,
                                             @Param("shortcode") String shortcode, @Param("firstname") String firstname,
                                             @Param("middlename") String middlename, @Param("lastname") String lastname);
}
