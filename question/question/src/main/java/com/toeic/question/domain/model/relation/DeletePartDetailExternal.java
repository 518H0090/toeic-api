package com.toeic.question.domain.model.relation;

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
public class DeletePartDetailExternal {
    @NotNull(message = "part_detail_id must not be empty")
    @Min(value = 1, message = "part_detail_id must be greater than 0")
    private int part_id;

    @NotNull(message = "question_id must not be empty")
    @Builder.Default
    private int question_id = 0;

    @NotNull(message = "paragraph_id must not be empty")
    @Builder.Default
    private int paragraph_id = 0;
}
