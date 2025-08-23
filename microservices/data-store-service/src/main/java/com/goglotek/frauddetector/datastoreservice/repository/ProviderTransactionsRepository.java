package com.goglotek.frauddetector.datastoreservice.repository;

import com.goglotek.frauddetector.datastoreservice.model.ProviderTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProviderTransactionsRepository extends JpaRepository<ProviderTransactions, Long> {
    @Query(value = "SELECT m FROM ProviderTransactions m WHERE LOWER(transactionId) LIKE %:id% OR LOWER(details) LIKE %:details% OR LOWER(clientAccount) LIKE %:clientAccount%")
    public List<ProviderTransactions> search(@Param("id") String transactionId, @Param("details") String details, @Param("clientAccount") String clientAccount);
}
