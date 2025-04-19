package com.toeic.question.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toeic.question.application.dto.AnswerDto;
import com.toeic.question.application.dto.add.AddAnswerDto;
import com.toeic.question.application.dto.update.UpdateAnswerDto;
import com.toeic.question.application.mapper.AnswerMapper;
import com.toeic.question.application.service.IAnswerService;
import com.toeic.question.domain.exception.BadRequestException;
import com.toeic.question.domain.exception.NotFoundException;
import com.toeic.question.domain.model.external.AnswerExternal;
import com.toeic.question.domain.model.internal.Answers;
import com.toeic.question.domain.repository.IAnswerRepository;
import com.toeic.question.domain.repository.IQuestionRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class AnswerService implements IAnswerService {
    
    private final IAnswerRepository _answerRepository;
    private final IQuestionRepository _questionRepository;
    
    @Override
    @Transactional
    public AnswerDto addAnswer(AddAnswerDto answerDto) {
        if (answerDto == null) throw new BadRequestException("answerDto is null");
        var findQuestion = _questionRepository.findQuestionByIdIncludeAnswers(answerDto.getQuestion_id())
            .orElseThrow(() -> new NotFoundException("Cannot find question with id: " + answerDto.getQuestion_id()));

        Answers answer = AnswerMapper.mapToAnswerFromAddDto(answerDto);
        answer.setOrder_number(findQuestion.getAnswers().size() + 1);
        answer.setQuestion(findQuestion);
        Answers savedAnswer = _answerRepository.save(answer);

        return AnswerMapper.mapToAnswerDto(savedAnswer);
    }

    @Override
    @Transactional
    public void updateAnswer(UpdateAnswerDto updateAnswerDto) {
        var findAnswer = _answerRepository.findById(updateAnswerDto.getAnswer_id())
            .orElseThrow(() -> new NotFoundException("Cannot find answer with id: " + updateAnswerDto.getAnswer_id()));

        var findQuestion = _questionRepository.findById(updateAnswerDto.getQuestion_id())
            .orElseThrow(() -> new NotFoundException("Cannot find question with id: " + updateAnswerDto.getQuestion_id()));

        var updatedAnswer = AnswerMapper.mapToAnswer(updateAnswerDto, findAnswer);
        updatedAnswer.setQuestion(findQuestion);

        _answerRepository.save(updatedAnswer);
    }

    @Override
    public AnswerDto getAnswerById(int id) {
        var findAnswer = _answerRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find answer with id: " + id));
        return AnswerMapper.mapToAnswerDto(findAnswer);
    }

    @Override
    public List<AnswerDto> getAnswers() {
        var allAnswers = _answerRepository.findAll();
        return allAnswers.stream().map(AnswerMapper::mapToAnswerDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAnswer(int id) {
        var findAnswer = _answerRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find answer with id: " + id));
        _answerRepository.delete(findAnswer);
    }

    @Override
    @Transactional
    public AnswerExternal addAnswerKafka(AnswerExternal addAnswer) {
        if (addAnswer == null) throw new BadRequestException("addAnswer is null");
        var findQuestion = _questionRepository.findQuestionByIdIncludeAnswers(addAnswer.getQuestion_id())
            .orElseThrow(() -> new NotFoundException("Cannot find question with id: " + addAnswer.getQuestion_id()));

        Answers answer = AnswerMapper.mapToAnswerExternal(addAnswer);
        answer.setOrder_number(findQuestion.getAnswers().size() + 1);
        answer.setQuestion(findQuestion);
        Answers savedAnswer = _answerRepository.save(answer);

        return AnswerMapper.mapToAnswerExternal(savedAnswer);
    }

    @Override
    @Transactional
    public AnswerExternal updateAnswerKafka(AnswerExternal updateAnswer) {
        var findAnswer = _answerRepository.findById(updateAnswer.getAnswer_id())
            .orElseThrow(() -> new NotFoundException("Cannot find answer with id: " + updateAnswer.getAnswer_id()));

        var findQuestion = _questionRepository.findById(updateAnswer.getQuestion_id())
            .orElseThrow(() -> new NotFoundException("Cannot find question with id: " + updateAnswer.getQuestion_id()));

        var updatedAnswer = AnswerMapper.mapToAnswerExternal(updateAnswer, findAnswer);
        updatedAnswer.setQuestion(findQuestion);

        Answers savedAnswer = _answerRepository.save(updatedAnswer);

        return AnswerMapper.mapToAnswerExternal(savedAnswer);
    }

}
