package com.toeic.user.application.dto.relation;

import java.sql.Timestamp;
import java.util.List;

import com.toeic.user.application.dto.TestSessionDetailDto;
import com.toeic.user.domain.enums.SessionType;
import com.toeic.user.domain.model.external.relation.ExamIncludePartExternal;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestSessionPartsDto {
    @NotNull(message = "test_session_id must not be empty")
    @Min(value = 1, message = "test_session_id must be greater than 0")
    private int test_session_id;

    @NotNull(message = "user_id must not be empty")
    @Min(value = 1, message = "user_id must be greater than 0")
    private int user_id;
    
    @Valid
    private ExamIncludePartExternal exam;

    @NotNull(message = "start_time must not be empty")
    private Timestamp start_time;

    private Timestamp end_time;

    @NotNull(message = "score must not be empty")
    @Min(value = 0, message = "score must at least equal 0")
    private int score;

    @Enumerated(EnumType.STRING)
    private SessionType status;

    @NotNull(message = "created_at must not be empty")
    private Timestamp created_at;

    @Valid
    private List<TestSessionDetailDto> test_session_details;
}
