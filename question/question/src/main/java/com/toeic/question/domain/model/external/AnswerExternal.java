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
public class AnswerExternal {
    private int answer_id;

    private int question_id;

    private String text;

    private Boolean is_correct;

    private int order_number;

    private Timestamp created_at;

    private String event_type;
}
