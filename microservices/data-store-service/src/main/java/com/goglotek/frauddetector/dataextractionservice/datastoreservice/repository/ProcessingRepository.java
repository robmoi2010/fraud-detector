package com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Processing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessingRepository extends JpaRepository<Processing, Long> {
}
