package com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.GroupAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupAccountsRepository extends JpaRepository<GroupAccounts, Long> {

    GroupAccounts findByAccount(String account);
}
