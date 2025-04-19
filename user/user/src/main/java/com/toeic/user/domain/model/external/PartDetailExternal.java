package com.toeic.user.domain.model.external;

import java.sql.Timestamp;

import com.toeic.user.domain.model.external.relation.ParagraphIncludeQuestionExternal;
import com.toeic.user.domain.model.external.relation.QuestionIncludeAnswerExternal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartDetailExternal {
    private int part_detail_id;

    private PartExternal part;

    private QuestionIncludeAnswerExternal question;

    private ParagraphIncludeQuestionExternal paragraph;

    private int order_number;

    private Timestamp created_at;
}
