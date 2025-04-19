package com.toeic.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toeic.user.domain.model.internal.TestSessions;

@Repository
public interface ITestSessionRepository extends JpaRepository<TestSessions, Integer> {

}
