package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFiles;

@Repository
public interface MpesaFilesRepository extends JpaRepository<MpesaFiles, Long> {
	@Query(value = "SELECT m FROM MpesaFiles m WHERE LOWER(fileName) LIKE %:file% OR LOWER(accountHolder) LIKE %:accountH% OR LOWER(shortcode) LIKE %:shortcode% OR LOWER(account) LIKE %:account% OR LOWER(organization) LIKE %:org%")
	List<MpesaFiles> search(@Param("file") String fileName, @Param("accountH") String accountHolder,
			@Param("shortcode") String shortcode, @Param("account") String account, @Param("org") String organization);
}
