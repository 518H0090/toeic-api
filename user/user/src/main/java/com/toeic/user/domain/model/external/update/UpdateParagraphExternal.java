package com.toeic.user.domain.model.external.update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParagraphExternal {
    @NotNull(message = "paragraph_id must not be empty")
    @Min(value = 1, message = "paragraph_id must be greater than 0")
    private int paragraph_id;

    @NotBlank(message = "content must not be empty")
    private String content;
    
    private Boolean is_public;
}
