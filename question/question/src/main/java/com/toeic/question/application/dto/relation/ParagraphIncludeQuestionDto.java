package com.toeic.question.application.dto.relation;

import java.sql.Timestamp;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ParagraphIncludeQuestionDto {
    @NotNull(message = "paragraph_id must not be empty")
    @Min(value = 1, message = "paragraph_id must be greater than 0")
    private int paragraph_id;

    @NotBlank(message = "content must not be empty")
    private String content;

    @NotNull(message = "created_by must not be empty")
    @Min(value = 1, message = "created_by must be greater than 0")
    private int created_by;

    @NotNull(message = "created_at must not be empty")
    private Timestamp created_at;

    private Boolean is_public;

    @Valid
    private List<QuestionIncludeAnswerDto> questions;
}
