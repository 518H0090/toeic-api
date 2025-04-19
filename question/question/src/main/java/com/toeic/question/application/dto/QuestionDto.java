package com.toeic.question.application.dto;

import java.sql.Timestamp;

import com.toeic.question.domain.enums.ContentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class QuestionDto {

    @NotNull(message = "question_id must not be empty")
    @Min(value = 1, message = "question_id must be greater than 0")
    private int question_id;

    @NotBlank(message = "description must not be empty")
    private String description;
    
    private Boolean is_multiple_choice;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @NotBlank(message = "content must not be empty")
    private String content;

    @NotNull(message = "paragraph_id must not be empty")
    private int paragraph_id;

    @NotNull(message = "created_by must not be empty")
    @Min(value = 1, message = "created_by must be greater than 0")
    private int created_by;

    @NotNull(message = "created_at must not be empty")
    private Timestamp created_at;

    private Boolean is_public;
}
