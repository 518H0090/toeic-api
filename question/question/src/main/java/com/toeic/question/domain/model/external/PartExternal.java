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
public class PartExternal {
    private int part_id;

    private String part_name;
   
    private int created_by;

    private Timestamp created_at;

    private Boolean is_public;

    private int exam_id;

    private String event_type;
}
