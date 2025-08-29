package com.goglotek.frauddetector.datastoreservice.repository;

import com.goglotek.frauddetector.datastoreservice.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FilesRepository extends JpaRepository<Files, Long> {
    @Query(value = "SELECT m FROM Files m WHERE LOWER(fileName) LIKE %:file% OR LOWER(groupAccount) LIKE %:account%")
    List<Files> search(@Param("file") String fileName, @Param("account") String account);
    Optional<Files> findFirstByGlobalId(String globalId);
}
