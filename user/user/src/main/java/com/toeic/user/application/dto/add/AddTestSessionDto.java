package com.toeic.user.application.dto.add;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTestSessionDto {

    @NotNull(message = "user_id must not be empty")
    @Min(value = 1, message = "user_id must be greater than 0")
    private int user_id;

    @NotNull(message = "exam_id must not be empty")
    @Min(value = 1, message = "exam_id must be greater than 0")
    private int exam_id;
}
