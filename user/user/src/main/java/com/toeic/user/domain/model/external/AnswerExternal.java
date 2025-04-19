package com.toeic.user.domain.model.external;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerExternal {

    private int answer_id;

    private int question_id;

    private String text;

    private Boolean is_correct;

    private int order_number;

    private Timestamp created_at;

    private String event_type;

    public AnswerExternal(int question_id, String text, Boolean is_correct, String event_type) {
        this.question_id = question_id;
        this.text = text;
        this.is_correct = is_correct;
        this.event_type = event_type;
    }

    public AnswerExternal(int answer_id, int question_id, String text, Boolean is_correct, int order_number,
            String event_type) {
        this.answer_id = answer_id;
        this.question_id = question_id;
        this.text = text;
        this.is_correct = is_correct;
        this.order_number = order_number;
        this.event_type = event_type;
    }

    public AnswerExternal(int answer_id, String event_type) {
        this.answer_id = answer_id;
        this.event_type = event_type;
    }
}
