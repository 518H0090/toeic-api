package com.toeic.question.application.dto;

import java.sql.Timestamp;

import com.toeic.question.application.dto.relation.ParagraphIncludeQuestionDto;
import com.toeic.question.application.dto.relation.QuestionIncludeAnswerDto;

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
public class PartDetailDto {
    
    @NotNull(message = "part_detail_id must not be empty")
    @Min(value = 1, message = "part_detail_id must be greater than 0")
    private int part_detail_id;

    @Valid
    private PartDto part;

    @Valid
    private QuestionIncludeAnswerDto question;

    @Valid
    private ParagraphIncludeQuestionDto paragraph;

    @NotNull(message = "order_number must not be empty")
    @Min(value = 1, message = "order_number must be greater than 0")
    private int order_number;

    @NotNull(message = "created_at must not be empty")
    private Timestamp created_at;
}
