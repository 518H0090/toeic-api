package com.toeic.user.domain.model.external.add;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AddAnswerExternal {
    @NotNull(message = "question_id must not be empty")
    @Min(value = 1, message = "question_id must be greater than 0")
    private int question_id;

    @NotBlank(message = "text must not be empty")
    @Length(max = 50, message = "length of text must be less than 50")
    private String text;

    private Boolean is_correct;
}
