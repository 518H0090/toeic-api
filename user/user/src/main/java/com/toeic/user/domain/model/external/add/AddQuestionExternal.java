package com.toeic.user.domain.model.external.add;

import com.toeic.user.domain.enums.ContentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AddQuestionExternal {
    @NotBlank(message = "description must not be empty")
    private String description;
    
    private Boolean is_multiple_choice;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @NotBlank(message = "content must not be empty")
    private String content;

    @NotNull(message = "paragraph_id must not be empty")
    @Builder.Default
    private int paragraph_id = 0;

    @NotNull(message = "created_by must not be empty")
    @Min(value = 1, message = "created_by must be greater than 0")
    private int created_by;

    private Boolean is_public;
}
