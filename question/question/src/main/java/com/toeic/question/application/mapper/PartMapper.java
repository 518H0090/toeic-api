package com.toeic.question.application.mapper;

import com.toeic.question.application.dto.PartDto;
import com.toeic.question.application.dto.add.AddPartDto;
import com.toeic.question.application.dto.update.UpdatePartDto;
import com.toeic.question.domain.model.external.PartExternal;
import com.toeic.question.domain.model.internal.Parts;

public class PartMapper {

    public static PartDto mapToPartDto(Parts part) {

        if (part == null)
            throw new NullPointerException("part is null");

        return PartDto.builder()
                .part_id(part.getPart_id())
                .part_name(part.getPart_name())
                .created_by(part.getCreated_by())
                .created_at(part.getCreated_at())
                .is_public(part.is_public())
                .exam_id(part.getExam().getExam_id())
                .build();
    }

    public static Parts mapToPartFromAddDto(AddPartDto addPartDto) {

        if (addPartDto == null)
            throw new NullPointerException("addPartDto is null");

        return Parts.builder()
                .part_name(addPartDto.getPart_name())
                .created_by(addPartDto.getCreated_by())
                .is_public(addPartDto.getIs_public())
                .build();
    }

    public static Parts mapToPart(UpdatePartDto updatePartDto, Parts part) {

        if (updatePartDto == null)
            throw new NullPointerException("updatePartDto is null");
        if (part == null)
            throw new NullPointerException("part is null");

        part.setPart_id(updatePartDto.getPart_id());
        part.setPart_name(updatePartDto.getPart_name());
        part.set_public(updatePartDto.getIs_public());

        return part;
    }

    public static Parts mapToPartExternal(PartExternal addPart) {

        if (addPart == null)
            throw new NullPointerException("addPart is null");

        return Parts.builder()
                .part_name(addPart.getPart_name())
                .created_by(addPart.getCreated_by())
                .is_public(addPart.getIs_public())
                .build();
    }

    public static PartExternal mapToPartExternal(Parts part) {

        if (part == null)
            throw new NullPointerException("part is null");

        return PartExternal.builder()
                .part_id(part.getPart_id())
                .part_name(part.getPart_name())
                .created_by(part.getCreated_by())
                .created_at(part.getCreated_at())
                .is_public(part.is_public())
                .exam_id(part.getExam().getExam_id())
                .build();
    }

    public static Parts mapToPartExternal(PartExternal updatePart, Parts part) {

        if (updatePart == null)
            throw new NullPointerException("updatePart is null");
        if (part == null)
            throw new NullPointerException("part is null");

        part.setPart_id(updatePart.getPart_id());
        part.setPart_name(updatePart.getPart_name());
        part.set_public(updatePart.getIs_public());

        return part;
    }
}
