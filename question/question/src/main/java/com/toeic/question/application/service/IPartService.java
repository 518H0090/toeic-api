package com.toeic.question.application.service;

import java.util.List;

import com.toeic.question.application.dto.PartDto;
import com.toeic.question.application.dto.add.AddPartDto;
import com.toeic.question.application.dto.update.UpdatePartDto;
import com.toeic.question.domain.model.external.PartExternal;

public interface IPartService {
    PartDto addPart(AddPartDto addPartDto);
    void updatePart(UpdatePartDto updatePartDto);
    PartDto getPartById(int id);
    List<PartDto> getParts();
    void deletePart(int id);

    PartExternal addPartKafka(PartExternal addPart);
    PartExternal updatePartKafka(PartExternal updatePart);
}
