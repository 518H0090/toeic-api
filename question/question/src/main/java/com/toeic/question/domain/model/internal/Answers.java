package com.toeic.question.domain.model.internal;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "answers")
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answer_id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Questions question;

    @Column(length = 50, nullable = false)
    private String text;

    private boolean is_correct;

    @Column(nullable = false)
    @Min(1)
    private int order_number;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;
}
