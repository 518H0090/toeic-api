package com.toeic.question.domain.model.external;

import java.sql.Timestamp;

import com.toeic.question.domain.enums.ContentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionExternal {

    private int question_id;

    private String description;
    
    private Boolean is_multiple_choice;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private String content;

    private int paragraph_id;

    private int created_by;

    private Timestamp created_at;

    private Boolean is_public;

    private String event_type;
}
