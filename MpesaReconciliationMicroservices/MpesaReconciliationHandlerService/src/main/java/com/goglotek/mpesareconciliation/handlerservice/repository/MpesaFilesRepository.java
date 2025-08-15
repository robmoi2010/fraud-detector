package com.goglotek.mpesareconciliation.handlerservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.goglotek.mpesareconciliation.handlerservice.model.MpesaFiles;

@Repository
public interface MpesaFilesRepository extends JpaRepository<MpesaFiles, Long> {
	MpesaFiles findByFileName(String fileName);

	List<MpesaFiles> findByProcessed(boolean processed);

	@Procedure(procedureName = "reconcile_transactions")
	int reconcileTransactions(long fileId);
}
