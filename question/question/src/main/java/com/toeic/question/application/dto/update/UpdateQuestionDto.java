package com.toeic.question.application.dto.update;

import com.toeic.question.domain.enums.ContentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class UpdateQuestionDto {
    @NotNull(message = "question_id must not be empty")
    private int question_id;

    @NotBlank(message = "description must not be empty")
    private String description;
    
    private Boolean is_multiple_choice;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @NotBlank(message = "content must not be empty")
    private String content;

    @NotNull(message = "paragraph_id must not be empty")
    @Builder.Default
    private int paragraph_id = 0;

    private Boolean is_public;
}
