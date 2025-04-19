package com.toeic.user.domain.model.external.relation;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.toeic.user.domain.model.external.PartExternal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamIncludePartExternal {
    @NotNull(message = "exam_id must not be empty")
    @Min(value = 1, message = "exam_id must be greater than 0")
    private int exam_id;

    @NotBlank(message = "exam_name must not be empty")
    @Length(max = 50, message = "length of exam_name must be less than 50")
    private String exam_name;

    @NotBlank(message = "exam_name must not be empty")
    private String description;

    @NotNull(message = "created_by must not be empty")
    @Min(value = 1, message = "created_by must be greater than 0")
    private int created_by;

    @NotNull(message = "created_at must not be empty")
    private Timestamp created_at;

    private Boolean is_public;

    @Valid
    private List<PartExternal> parts;
}
