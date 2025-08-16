package com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.ProviderLocalTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProviderLocalTransactionsRepository extends JpaRepository<ProviderLocalTransactions, Long> {
    @Query(value = "SELECT m FROM ProviderLocalTransactions m WHERE comments LIKE %:comments% OR action LIKE %:action%")
    List<ProviderLocalTransactions> search(@Param("comments") String comments, @Param("action") String action);
}
