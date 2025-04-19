package com.toeic.user.domain.model.external.add;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddExamExternal {
    @NotBlank(message = "exam_name must not be empty")
    @Length(max = 50, message = "length of exam_name must be less than 50")
    private String exam_name;

    @NotBlank(message = "exam_name must not be empty")
    private String description;

    @NotNull(message = "created_by must not be empty")
    @Min(value = 1, message = "created_by must be greater than 0")
    private int created_by;

    private Boolean is_public;
}
