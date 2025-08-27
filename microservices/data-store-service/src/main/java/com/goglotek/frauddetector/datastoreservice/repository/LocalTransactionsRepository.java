package com.goglotek.frauddetector.datastoreservice.repository;

import com.goglotek.frauddetector.datastoreservice.model.LocalTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LocalTransactionsRepository extends JpaRepository<LocalTransactions, Long> {
    @Query(value = "SELECT l FROM LocalTransactions l WHERE transactionTime BETWEEN :from AND :to")
    List<LocalTransactions> findByTimePeriod(@Param("from") Date from, @Param("to") Date to);
}
