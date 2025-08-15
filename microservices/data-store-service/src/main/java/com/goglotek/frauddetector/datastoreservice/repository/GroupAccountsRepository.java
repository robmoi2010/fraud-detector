package com.goglotek.frauddetector.datastoreservice.repository;

import com.goglotek.frauddetector.datastoreservice.model.GroupAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupAccountsRepository extends JpaRepository<GroupAccounts, Long> {

    GroupAccounts findByAccount(String account);
}
