package com.toeic.user.domain.model.external;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParagraphExternal {

    private int paragraph_id;

    private String content;

    private int created_by;

    private Timestamp created_at;

    private Boolean is_public;

    private String event_type;

    public ParagraphExternal(String content, int created_by, Boolean is_public, String event_type) {
        this.content = content;
        this.created_by = created_by;
        this.is_public = is_public;
        this.event_type = event_type;
    }

    public ParagraphExternal(int paragraph_id, String content, Boolean is_public, String event_type) {
        this.paragraph_id = paragraph_id;
        this.content = content;
        this.is_public = is_public;
        this.event_type = event_type;
    }

    public ParagraphExternal(int paragraphId, String event_type) {
        this.paragraph_id = paragraphId;
        this.event_type = event_type;
    }
}
