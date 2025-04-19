package com.toeic.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toeic.question.domain.model.internal.Parts;

@Repository
public interface IPartRepository extends JpaRepository<Parts, Integer> {

}
