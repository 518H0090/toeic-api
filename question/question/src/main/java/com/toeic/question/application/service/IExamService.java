package com.toeic.question.application.service;

import java.util.List;

import com.toeic.question.application.dto.ExamDto;
import com.toeic.question.application.dto.add.AddExamDto;
import com.toeic.question.application.dto.relation.ExamIncludePartDto;
import com.toeic.question.application.dto.update.UpdateExamDto;
import com.toeic.question.domain.model.external.ExamExternal;

public interface IExamService {
    ExamDto addExam(AddExamDto addExamDto);
    void updateExam(UpdateExamDto updateExamDto);
    ExamDto getExamById(int id);
    List<ExamDto> getExams();
    void deleteExam(int id);
    ExamExternal addExamKafka(ExamExternal addExam);
    ExamExternal updateExamKafka(ExamExternal updateExam);
    ExamIncludePartDto getExamIncludePartById(int id);
}
