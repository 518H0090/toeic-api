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
public class PartDetailExternal {
    private int part_detail_id;

    private PartExternal part;

    private QuestionExternal question;

    private ParagraphExternal paragraph;

    private int order_number;

    private Timestamp created_at;
}
