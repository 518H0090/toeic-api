package com.toeic.question.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toeic.question.application.dto.QuestionDto;
import com.toeic.question.application.dto.add.AddQuestionDto;
import com.toeic.question.application.dto.update.UpdateQuestionDto;
import com.toeic.question.application.mapper.QuestionMapper;
import com.toeic.question.application.service.IQuestionService;
import com.toeic.question.domain.exception.BadRequestException;
import com.toeic.question.domain.exception.NotFoundException;
import com.toeic.question.domain.model.external.QuestionExternal;
import com.toeic.question.domain.model.internal.Questions;
import com.toeic.question.domain.repository.IParagraphRepository;
import com.toeic.question.domain.repository.IQuestionRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class QuestionService implements IQuestionService {

    private final IParagraphRepository _paragraphRepository;
    private final IQuestionRepository _questionRepository;

    @Override
    @Transactional
    public QuestionDto addQuestion(AddQuestionDto addQuestionDto) {
        if (addQuestionDto == null)
            throw new BadRequestException("addQuestionDto is null");

        Questions question = QuestionMapper.mapToQuestionFromAddDto(addQuestionDto);

        if (addQuestionDto.getParagraph_id() > 0) {
            var findParagraph = _paragraphRepository.findById(addQuestionDto.getParagraph_id())
                    .orElseThrow(() -> new NotFoundException(
                            "Cannot find paragraph with id: " + addQuestionDto.getParagraph_id()));
            question.setParagraph(findParagraph);
        }

        Questions savedQuestion = _questionRepository.save(question);

        return QuestionMapper.mapToQuestionDto(savedQuestion);
    }

    @Override
    @Transactional
    public void updateQuestion(UpdateQuestionDto updateQuestionDto) {
        var findQuestion = _questionRepository.findById(updateQuestionDto.getQuestion_id())
                .orElseThrow(() -> new NotFoundException(
                        "Cannot find question with id: " + updateQuestionDto.getQuestion_id()));

        var updatedQuestion = QuestionMapper.mapToQuestion(updateQuestionDto, findQuestion);

        if (updateQuestionDto.getParagraph_id() > 0) {
            var findParagraph = _paragraphRepository.findById(updateQuestionDto.getParagraph_id())
                    .orElseThrow(() -> new NotFoundException(
                            "Cannot find paragraph with id: " + updateQuestionDto.getParagraph_id()));

            updatedQuestion.setParagraph(findParagraph);
        }

        _questionRepository.save(updatedQuestion);
    }

    @Override
    public QuestionDto getQuestionById(int id) {
        var findQuestion = _questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find question with id: " + id));
        return QuestionMapper.mapToQuestionDto(findQuestion);
    }

    @Override
    public List<QuestionDto> getQuestions() {
        var allQuestions = _questionRepository.findAll();
        return allQuestions.stream().map(QuestionMapper::mapToQuestionDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteQuestion(int id) {
        var findQuestion = _questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find question with id: " + id));
        _questionRepository.delete(findQuestion);
    }

    @Override
    @Transactional
    public QuestionExternal addQuestionKafka(QuestionExternal addQuestion) {
        if (addQuestion == null)
            throw new BadRequestException("addQuestion is null");

        Questions question = QuestionMapper.mapToQuestionExternal(addQuestion);

        if (addQuestion.getParagraph_id() > 0) {
            var findParagraph = _paragraphRepository.findById(addQuestion.getParagraph_id())
                    .orElseThrow(() -> new NotFoundException(
                            "Cannot find paragraph with id: " + addQuestion.getParagraph_id()));
            question.setParagraph(findParagraph);
        }

        Questions savedQuestion = _questionRepository.save(question);

        return QuestionMapper.mapToQuestionExternal(savedQuestion);
    }

    @Override
    @Transactional
    public QuestionExternal updateQuestionKafka(QuestionExternal updateQuestion) {
        var findQuestion = _questionRepository.findById(updateQuestion.getQuestion_id())
                .orElseThrow(() -> new NotFoundException(
                        "Cannot find question with id: " + updateQuestion.getQuestion_id()));

        var updatedQuestion = QuestionMapper.mapToQuestionExternal(updateQuestion, findQuestion);

        if (updateQuestion.getParagraph_id() > 0) {
            var findParagraph = _paragraphRepository.findById(updateQuestion.getParagraph_id())
                    .orElseThrow(() -> new NotFoundException(
                            "Cannot find paragraph with id: " + updateQuestion.getParagraph_id()));

            updatedQuestion.setParagraph(findParagraph);
        }

        Questions savedQuestion = _questionRepository.save(updatedQuestion);

        return QuestionMapper.mapToQuestionExternal(savedQuestion);
    }

}
