package com.goglotek.frauddetector.datastoreservice.repository;

import com.goglotek.frauddetector.datastoreservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}
