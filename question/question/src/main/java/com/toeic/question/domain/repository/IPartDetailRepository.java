package com.toeic.question.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.toeic.question.domain.model.internal.PartDetails;

@Repository
public interface IPartDetailRepository extends JpaRepository<PartDetails, Integer> {
    @Query("SELECT pd FROM PartDetails pd WHERE pd.part.id = :part_id AND pd.paragraph.id = :paragraph_id")
    Optional<PartDetails> findPartDetailsWithParagraph(int part_id, int paragraph_id);

    @Query("SELECT pd FROM PartDetails pd WHERE pd.part.id = :part_id AND pd.question.id = :question_id")
    Optional<PartDetails> findPartDetailsWithQuestion(int part_id, int question_id);

    @EntityGraph(attributePaths = {"part", "question", "paragraph"})
    @Query("SELECT pd FROM PartDetails pd")
    List<PartDetails> findAllPartDetails();

    @EntityGraph(attributePaths = {"part", "question", "paragraph"})
    @Query("SELECT pd FROM PartDetails pd WHERE pd.part.id = :part_id")
    List<PartDetails> findPartDetailsByPartId(int part_id);
}
