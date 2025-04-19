package com.toeic.user.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.toeic.user.domain.model.internal.TestSessionDetails;

@Repository
public interface ITestSessionDetailRepository extends JpaRepository<TestSessionDetails, Integer> {
    @Query("SELECT tsd FROM TestSessionDetails tsd WHERE tsd.question_id = :question_id AND tsd.test_session.id = :test_session_id")
    Optional<TestSessionDetails> findPartDetailsWithQuestion(int question_id, int test_session_id);
}
