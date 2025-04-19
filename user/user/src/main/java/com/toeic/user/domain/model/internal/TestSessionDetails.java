package com.toeic.user.domain.model.internal;

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
@Table(name = "test_session_details")
public class TestSessionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int test_session_detail_id;

    @ManyToOne
    @JoinColumn(name = "test_session_id")
    private TestSessions test_session;

    @Column(nullable = false)
    @Min(1)
    private int question_id;

    @Column(nullable = false)
    private String selected_answer;

    private boolean is_correct;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;
}
