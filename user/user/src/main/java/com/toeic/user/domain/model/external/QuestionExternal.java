package com.toeic.user.domain.model.external;

import java.time.OffsetDateTime;

import com.toeic.user.domain.enums.ContentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionExternal {

    private int question_id;

    private String description;

    private Boolean is_multiple_choice;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private String content;

    private int paragraph_id;

    private int created_by;

    private OffsetDateTime created_at;

    private Boolean is_public;

    private String event_type;

    public QuestionExternal(String description, Boolean is_multiple_choice, ContentType contentType, String content,
            int paragraph_id, int created_by, Boolean is_public, String event_type) {
                this.description = description;
                this.is_multiple_choice = is_multiple_choice;
                this.contentType = contentType;
                this.content = content;
                this.paragraph_id = paragraph_id;
                this.created_by = created_by;
                this.is_public = is_public;
                this.event_type = event_type;
    }

    public QuestionExternal(int question_id, String description, Boolean is_multiple_choice,
            ContentType contentType, String content, int paragraph_id, Boolean is_public, String event_type) {
        this.question_id = question_id;
        this.description = description;
        this.is_multiple_choice = is_multiple_choice;
        this.contentType = contentType;
        this.content = content;
        this.paragraph_id = paragraph_id;
        this.is_public = is_public;
        this.event_type = event_type;
    }

    public QuestionExternal(int question_id, String event_type) {
        this.question_id = question_id;
        this.event_type = event_type;
    }
}
