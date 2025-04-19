package com.toeic.question.application.service;

import java.util.List;

import com.toeic.question.application.dto.PartDetailDto;
import com.toeic.question.application.dto.relation.AddPartDetailDto;
import com.toeic.question.application.dto.relation.DeletePartDetailDto;
import com.toeic.question.domain.model.external.PartDetailExternal;
import com.toeic.question.domain.model.relation.AddPartDetailExternal;
import com.toeic.question.domain.model.relation.DeletePartDetailExternal;

public interface IPartDetailService {
    void addPartDetail(AddPartDetailDto request);
    void deletePartDetail(DeletePartDetailDto request);
    List<PartDetailDto> getPartDetails();
    List<PartDetailDto> getPartDetailByPartId(int part_id);

    PartDetailExternal addPartDetailKafka(AddPartDetailExternal request);
    void deletePartDetailKafka(DeletePartDetailExternal request);
}
