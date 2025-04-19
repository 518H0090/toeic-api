package com.toeic.user.domain.model.external;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamExternal {
    private int exam_id;

    private String exam_name;

    private String description;
    
    private int created_by;

    private Timestamp created_at;

    private Boolean is_public;

    private String event_type;

    public ExamExternal(String exam_name, String description, int created_by, Boolean is_public, String event_type) {
        this.exam_name = exam_name;
        this.description = description;
        this.created_by = created_by;
        this.is_public = is_public;
        this.event_type = event_type;
    }

    public ExamExternal(int exam_id, String exam_name, String description, Boolean is_public, String event_type) {
        this.exam_id = exam_id;
        this.exam_name = exam_name;
        this.description = description;
        this.is_public = is_public;
        this.event_type = event_type;
    }

    public ExamExternal(int exam_id, String event_type) {
        this.exam_id = exam_id;
        this.event_type = event_type;
    }
}
