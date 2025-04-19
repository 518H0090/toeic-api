package com.toeic.question.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toeic.question.domain.model.internal.Questions;

@Repository
public interface IQuestionRepository extends JpaRepository<Questions, Integer> {
    @EntityGraph(attributePaths = {"answers"})
    @Query("SELECT q FROM Questions q WHERE q.question_id = :question_id")
    Optional<Questions> findQuestionByIdIncludeAnswers(@Param("question_id") Integer question_id);
}
