package com.toeic.question.application.service;

import java.util.List;

import com.toeic.question.application.dto.AnswerDto;
import com.toeic.question.application.dto.add.AddAnswerDto;
import com.toeic.question.application.dto.update.UpdateAnswerDto;
import com.toeic.question.domain.model.external.AnswerExternal;

public interface IAnswerService {
    AnswerDto addAnswer(AddAnswerDto answerDto);
    void updateAnswer(UpdateAnswerDto updateAnswerDto);
    AnswerDto getAnswerById(int id);
    List<AnswerDto> getAnswers();
    void deleteAnswer(int id);

    AnswerExternal addAnswerKafka(AnswerExternal addAnswer);
    AnswerExternal updateAnswerKafka(AnswerExternal updateAnswer);
}
