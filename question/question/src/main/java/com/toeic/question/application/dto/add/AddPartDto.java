package com.toeic.question.application.dto.add;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddPartDto {
    @NotBlank(message = "text must not be empty")
    @Length(max = 50, message = "length of part_name must be less than 50")
    private String part_name;
   
    @NotNull(message = "created_by must not be empty")
    @Min(value = 1, message = "created_by must be greater than 0")
    private int created_by;

    private Boolean is_public;

    @NotNull(message = "exam_id must not be empty")
    @Min(value = 1, message = "exam_id must be greater than 0")
    private int exam_id;
}
