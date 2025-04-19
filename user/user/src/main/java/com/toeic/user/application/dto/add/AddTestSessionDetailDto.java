package com.toeic.user.application.dto.add;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddTestSessionDetailDto {
    @NotNull(message = "test_session_id must not be empty")
    @Min(value = 1, message = "test_session_id must be greater than 0")
    private int test_session_id;

    @NotNull(message = "test_session_detail_id must not be empty")
    @Min(value = 1, message = "test_session_detail_id must be greater than 0")
    private int question_id;

    @NotBlank(message = "selected_answer must not be empty")
    private String selected_answer;

    private Boolean is_correct;
}
