package com.toeic.question.domain.model.internal;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "paragraphs")
public class Paragraphs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paragraph_id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int created_by;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;

    private boolean is_public;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paragraph")
    private List<Questions> questions;
}
