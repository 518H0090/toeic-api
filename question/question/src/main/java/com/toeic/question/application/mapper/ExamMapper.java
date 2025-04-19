package com.toeic.question.application.mapper;

import java.util.stream.Collectors;

import com.toeic.question.application.dto.ExamDto;
import com.toeic.question.application.dto.add.AddExamDto;
import com.toeic.question.application.dto.relation.ExamIncludePartDto;
import com.toeic.question.application.dto.update.UpdateExamDto;
import com.toeic.question.domain.model.external.ExamExternal;
import com.toeic.question.domain.model.internal.Exams;

public class ExamMapper {

    public static ExamDto mapToExamDto(Exams exam) {

        if (exam == null)
            throw new NullPointerException("exam is null");

        return ExamDto.builder()
                .exam_id(exam.getExam_id())
                .exam_name(exam.getExam_name())
                .description(exam.getDescription())
                .created_by(exam.getCreated_by())
                .created_at(exam.getCreated_at())
                .is_public(exam.is_public())
                .build();
    }

    public static Exams mapToExamFromAddDto(AddExamDto addExamDto) {

        if (addExamDto == null)
            throw new NullPointerException("addExamDto is null");

        return Exams.builder()
                .exam_name(addExamDto.getExam_name())
                .description(addExamDto.getDescription())
                .created_by(addExamDto.getCreated_by())
                .is_public(addExamDto.getIs_public())
                .build();
    }

    public static Exams mapToExam(UpdateExamDto updateExamDto, Exams exam) {

        if (updateExamDto == null)
            throw new NullPointerException("updateExamDto is null");
        if (exam == null)
            throw new NullPointerException("exam is null");

        exam.setExam_id(updateExamDto.getExam_id());
        exam.setExam_name(updateExamDto.getExam_name());
        exam.setDescription(updateExamDto.getDescription());
        exam.set_public(updateExamDto.getIs_public());

        return exam;
    }

    public static Exams mapToExamExternal(ExamExternal addExam) {

        if (addExam == null)
            throw new NullPointerException("addExam is null");

        return Exams.builder()
                .exam_name(addExam.getExam_name())
                .description(addExam.getDescription())
                .created_by(addExam.getCreated_by())
                .is_public(addExam.getIs_public())
                .build();
    }

    public static ExamExternal mapToExamExternal(Exams exam) {

        if (exam == null)
            throw new NullPointerException("exam is null");

        return ExamExternal.builder()
                .exam_id(exam.getExam_id())
                .exam_name(exam.getExam_name())
                .description(exam.getDescription())
                .created_by(exam.getCreated_by())
                .created_at(exam.getCreated_at())
                .is_public(exam.is_public())
                .build();
    }

    public static Exams mapToExamExternal(ExamExternal updateExam, Exams exam) {

        if (updateExam == null)
            throw new NullPointerException("updateExam is null");
        if (exam == null)
            throw new NullPointerException("exam is null");

        exam.setExam_id(updateExam.getExam_id());
        exam.setExam_name(updateExam.getExam_name());
        exam.setDescription(updateExam.getDescription());
        exam.set_public(updateExam.getIs_public());

        return exam;
    }

    public static ExamIncludePartDto mapToExamIncludePartDto(Exams exam) {

        if (exam == null)
            throw new NullPointerException("exam is null");

        return ExamIncludePartDto.builder()
                .exam_id(exam.getExam_id())
                .exam_name(exam.getExam_name())
                .description(exam.getDescription())
                .created_by(exam.getCreated_by())
                .created_at(exam.getCreated_at())
                .is_public(exam.is_public())
                .parts(exam.getParts().stream().map(PartMapper::mapToPartDto).collect(Collectors.toList()))
                .build();
    }
}
