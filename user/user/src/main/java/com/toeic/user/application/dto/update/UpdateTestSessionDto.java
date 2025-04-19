package com.toeic.user.application.dto.update;

import com.toeic.user.domain.enums.SessionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class UpdateTestSessionDto {

    @NotNull(message = "test_session_id must not be empty")
    @Min(value = 1, message = "test_session_id must be greater than 0")
    private int test_session_id;

    @NotNull(message = "score must not be empty")
    @Min(value = 0, message = "score must at least equal 0")
    private int score;

    @Enumerated(EnumType.STRING)
    private SessionType status;
}
