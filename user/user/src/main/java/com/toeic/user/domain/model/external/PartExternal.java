package com.toeic.user.domain.model.external;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartExternal {

    private int part_id;

    private String part_name;
   
    private int created_by;

    private Timestamp created_at;

    private Boolean is_public;

    private int exam_id;

    private String event_type;

    public PartExternal(String part_name, int created_by, Boolean is_public, int exam_id, String event_type) {
        this.part_name = part_name;
        this.created_by = created_by;
        this.is_public = is_public;
        this.exam_id = exam_id;
        this.event_type = event_type;
    }

    public PartExternal(int part_id, String part_name, Boolean is_public, int exam_id, String event_type) {
        this.part_id = part_id;
        this.part_name = part_name;
        this.is_public = is_public;
        this.exam_id = exam_id;
        this.event_type = event_type;
    }

    public PartExternal(int part_id, String event_type) {
        this.part_id = part_id;
        this.event_type = event_type;
    }
}
