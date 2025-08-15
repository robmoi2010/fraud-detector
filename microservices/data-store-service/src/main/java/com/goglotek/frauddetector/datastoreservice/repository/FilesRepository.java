package com.goglotek.frauddetector.datastoreservice.repository;

import com.goglotek.frauddetector.datastoreservice.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long> {
    @Query(value = "SELECT m FROM Files m WHERE LOWER(fileName) LIKE %:file% OR LOWER(accountHolder) LIKE %:accountH% OR LOWER(shortcode) LIKE %:shortcode% OR LOWER(account) LIKE %:account% OR LOWER(organization) LIKE %:org%")
    List<Files> search(@Param("file") String fileName, @Param("accountH") String accountHolder,
                       @Param("shortcode") String shortcode, @Param("account") String account, @Param("org") String organization);
}
