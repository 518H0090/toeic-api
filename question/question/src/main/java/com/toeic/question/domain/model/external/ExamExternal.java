package com.toeic.question.domain.model.external;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamExternal {
    private int exam_id;

    private String exam_name;

    private String description;
    
    private int created_by;

    private Timestamp created_at;

    private Boolean is_public;

    private String event_type;
}
