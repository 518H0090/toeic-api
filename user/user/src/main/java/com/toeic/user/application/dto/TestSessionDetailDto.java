package com.toeic.user.application.dto;

import java.sql.Timestamp;

import com.toeic.user.domain.model.external.QuestionExternal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestSessionDetailDto {

    @NotNull(message = "test_session_detail_id must not be empty")
    @Min(value = 1, message = "test_session_detail_id must be greater than 0")
    private int test_session_detail_id;

    @Valid
    private TestSessionDto test_session;

    @Valid
    private QuestionExternal question;

    @NotBlank(message = "selected_answer must not be empty")
    private String selected_answer;

    private Boolean is_correct;

    @NotNull(message = "created_at must not be empty")
    private Timestamp created_at;
}
