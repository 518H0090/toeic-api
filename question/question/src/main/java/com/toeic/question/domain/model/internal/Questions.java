package com.toeic.question.domain.model.internal;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.toeic.question.domain.enums.ContentType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int question_id;

    @Column(nullable = false)
    private String description;

    private boolean is_multiple_choice;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "paragraph_id", nullable = true)
    private Paragraphs paragraph;

    @Column(nullable = false)
    private int created_by;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;

    private boolean is_public;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<Answers> answers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<PartDetails> part_details;
}

