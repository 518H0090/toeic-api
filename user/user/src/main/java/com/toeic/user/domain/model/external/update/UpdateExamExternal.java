package com.toeic.user.domain.model.external.update;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExamExternal {
    @NotNull(message = "exam_id must not be empty")
    @Min(value = 1, message = "exam_id must be greater than 0")
    private int exam_id;

    @NotBlank(message = "exam_name must not be empty")
    @Length(max = 50, message = "length of exam_name must be less than 50")
    private String exam_name;

    @NotBlank(message = "exam_name must not be empty")
    private String description;

    private Boolean is_public;
}
