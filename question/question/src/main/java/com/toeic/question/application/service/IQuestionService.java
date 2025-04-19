package com.toeic.question.application.service;

import java.util.List;

import com.toeic.question.application.dto.QuestionDto;
import com.toeic.question.application.dto.add.AddQuestionDto;
import com.toeic.question.application.dto.update.UpdateQuestionDto;
import com.toeic.question.domain.model.external.QuestionExternal;

public interface IQuestionService {
    QuestionDto addQuestion(AddQuestionDto addQuestionDto);
    void updateQuestion(UpdateQuestionDto updateQuestionDto);
    QuestionDto getQuestionById(int id);
    List<QuestionDto> getQuestions();
    void deleteQuestion(int id);

    QuestionExternal addQuestionKafka(QuestionExternal addQuestion);
    QuestionExternal updateQuestionKafka(QuestionExternal updateQuestion);
}
