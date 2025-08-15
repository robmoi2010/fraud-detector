package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaUser;

@Repository
public interface MpesaUserRepository extends JpaRepository<MpesaUser, Long> {
	public MpesaUser findByEmail(String email);
}
