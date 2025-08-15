package com.goglotek.frauddetector.datastoreservice.repository;

import com.goglotek.frauddetector.datastoreservice.model.LocalTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalTransactionsRepository extends JpaRepository<LocalTransactions, Long> {
}
