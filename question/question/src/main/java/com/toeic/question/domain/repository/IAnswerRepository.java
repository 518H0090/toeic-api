package com.toeic.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toeic.question.domain.model.internal.Answers;

@Repository
public interface IAnswerRepository extends JpaRepository<Answers, Integer> {

}
