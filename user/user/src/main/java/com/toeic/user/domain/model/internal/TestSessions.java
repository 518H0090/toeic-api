package com.toeic.user.domain.model.internal;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.toeic.user.domain.enums.SessionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "test_sessions")
public class TestSessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int test_session_id;

    @Column(nullable = false)
    @Min(1)
    private int user_id;

    @Column(nullable = false)
    @Min(1)
    private int exam_id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp start_time;

    @Column(updatable = true)
    private Timestamp end_time;

    @Column(nullable = false)
    @Min(0)
    private int score;

    @Enumerated(EnumType.STRING)
    private SessionType status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test_session")
    private List<TestSessionDetails> test_session_details;
}
