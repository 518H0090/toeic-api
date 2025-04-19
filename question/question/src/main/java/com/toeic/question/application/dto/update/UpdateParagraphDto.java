package com.toeic.question.application.dto.update;

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
public class UpdateParagraphDto {
    
    @NotNull(message = "paragraph_id must not be empty")
    @Min(value = 1, message = "paragraph_id must be greater than 0")
    private int paragraph_id;

    @NotBlank(message = "content must not be empty")
    private String content;
    
    private Boolean is_public;
}
