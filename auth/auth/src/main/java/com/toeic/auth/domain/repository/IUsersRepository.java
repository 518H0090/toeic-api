package com.toeic.auth.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toeic.auth.domain.model.Users;

@Repository
public interface IUsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByKeycloakId(String keycloakId);
}
