package com.toeic.question.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toeic.question.application.dto.ExamDto;
import com.toeic.question.application.dto.add.AddExamDto;
import com.toeic.question.application.dto.relation.ExamIncludePartDto;
import com.toeic.question.application.dto.update.UpdateExamDto;
import com.toeic.question.application.mapper.ExamMapper;
import com.toeic.question.application.service.IExamService;
import com.toeic.question.domain.exception.BadRequestException;
import com.toeic.question.domain.exception.NotFoundException;
import com.toeic.question.domain.model.external.ExamExternal;
import com.toeic.question.domain.model.internal.Exams;
import com.toeic.question.domain.repository.IExamRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class ExamService implements IExamService {

    private final IExamRepository _examRepository;

    @Override
    @Transactional
    public ExamDto addExam(AddExamDto addExamDto) {
        if (addExamDto == null)
            throw new BadRequestException("addExamDto is null");

        Exams exam = ExamMapper.mapToExamFromAddDto(addExamDto);
        Exams savedExam = _examRepository.save(exam);

        return ExamMapper.mapToExamDto(savedExam);
    }

    @Override
    @Transactional
    public void updateExam(UpdateExamDto updateExamDto) {
        var findExam = _examRepository.findById(updateExamDto.getExam_id())
                .orElseThrow(() -> new NotFoundException("Cannot find exam with id: " + updateExamDto.getExam_id()));

        var updatedExam = ExamMapper.mapToExam(updateExamDto, findExam);

        _examRepository.save(updatedExam);
    }

    @Override
    public ExamDto getExamById(int id) {
        var findExam = _examRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find exam with id: " + id));
        return ExamMapper.mapToExamDto(findExam);
    }

    @Override
    public List<ExamDto> getExams() {
        var allExams = _examRepository.findAll();
        return allExams.stream().map(ExamMapper::mapToExamDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteExam(int id) {
        var findExam = _examRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find exam with id: " + id));
        _examRepository.delete(findExam);
    }

    @Override
    @Transactional
    public ExamExternal addExamKafka(ExamExternal addExam) {
        if (addExam == null)
            throw new BadRequestException("addExam is null");

        Exams exam = ExamMapper.mapToExamExternal(addExam);
        Exams savedExam = _examRepository.save(exam);

        return ExamMapper.mapToExamExternal(savedExam);
    }

    @Override
    @Transactional
    public ExamExternal updateExamKafka(ExamExternal updateExam) {
        var findExam = _examRepository.findById(updateExam.getExam_id())
                .orElseThrow(() -> new NotFoundException("Cannot find exam with id: " + updateExam.getExam_id()));

        var updatedExam = ExamMapper.mapToExamExternal(updateExam, findExam);

        Exams savedExam = _examRepository.save(updatedExam);

        return ExamMapper.mapToExamExternal(savedExam);
    }

    @Override
    public ExamIncludePartDto getExamIncludePartById(int id) {
        var findExam = _examRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find exam with id: " + id));
        return ExamMapper.mapToExamIncludePartDto(findExam);
    }
}
