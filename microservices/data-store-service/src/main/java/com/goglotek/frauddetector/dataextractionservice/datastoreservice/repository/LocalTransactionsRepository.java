package com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.LocalTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalTransactionsRepository extends JpaRepository<LocalTransactions, Long> {
}
