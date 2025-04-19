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
public class ParagraphExternal {

    private int paragraph_id;

    private String content;

    private int created_by;

    private Timestamp created_at;

    private Boolean is_public;

    private String event_type;
}
