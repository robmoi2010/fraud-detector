package com.goglotek.frauddetector.datastoreservice.repository;

import com.goglotek.frauddetector.datastoreservice.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
}
