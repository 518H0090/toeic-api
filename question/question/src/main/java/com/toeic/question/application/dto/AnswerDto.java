package com.toeic.question.application.dto;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AnswerDto {

    @NotNull(message = "answer_id must not be empty")
    @Min(value = 1, message = "answer_id must be greater than 0")
    private int answer_id;

    @NotNull(message = "question_id must not be empty")
    @Min(value = 1, message = "question_id must be greater than 0")
    private int question_id;

    @NotBlank(message = "text must not be empty")
    @Length(max = 50, message = "length of text must be less than 50")
    private String text;

    private Boolean is_correct;

    @NotNull(message = "order_number must not be empty")
    @Min(value = 1, message = "order_number must be greater than 0")
    private int order_number;

    @NotNull(message = "created_at must not be empty")
    private Timestamp created_at;
}
