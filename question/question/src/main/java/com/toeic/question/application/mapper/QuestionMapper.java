package com.toeic.question.application.mapper;

import java.util.stream.Collectors;

import com.toeic.question.application.dto.QuestionDto;
import com.toeic.question.application.dto.add.AddQuestionDto;
import com.toeic.question.application.dto.relation.QuestionIncludeAnswerDto;
import com.toeic.question.application.dto.update.UpdateQuestionDto;
import com.toeic.question.domain.model.external.QuestionExternal;
import com.toeic.question.domain.model.internal.Questions;

public class QuestionMapper {

    public static QuestionDto mapToQuestionDto(Questions question) {

        if (question == null) throw new NullPointerException("question is null");

        QuestionDto questionDto = QuestionDto.builder()
                                    .question_id(question.getQuestion_id())
                                    .description(question.getDescription())
                                    .is_multiple_choice(question.is_multiple_choice())
                                    .contentType(question.getContentType())
                                    .content(question.getContent())
                                    .created_by(question.getCreated_by())
                                    .created_at(question.getCreated_at())
                                    .is_public(question.is_public())
                                    .build();

        if (question.getParagraph() != null) {
            questionDto.setParagraph_id(question.getParagraph().getParagraph_id());
        }

        return questionDto;
    }

    public static Questions mapToQuestionFromAddDto(AddQuestionDto addQuestionDto) {

        if (addQuestionDto == null) throw new NullPointerException("addQuestionDto is null");

        return Questions.builder()
                    .description(addQuestionDto.getDescription())
                    .is_multiple_choice(addQuestionDto.getIs_multiple_choice())
                    .contentType(addQuestionDto.getContentType())
                    .content(addQuestionDto.getContent())
                    .created_by(addQuestionDto.getCreated_by())
                    .is_public(addQuestionDto.getIs_public())
                    .build();
    }

     public static Questions mapToQuestion(UpdateQuestionDto updateQuestionDto, Questions question) {

        if (updateQuestionDto == null) throw new NullPointerException("updateQuestionDto is null");
        if (question == null) throw new NullPointerException("question is null");

        question.setQuestion_id(updateQuestionDto.getQuestion_id());
        question.setDescription(updateQuestionDto.getDescription());
        question.set_multiple_choice(updateQuestionDto.getIs_multiple_choice());
        question.setContentType(question.getContentType());
        question.setContent(updateQuestionDto.getContent());
        question.set_public(updateQuestionDto.getIs_public());

        return question;
    }

    public static Questions mapToQuestionExternal(QuestionExternal addQuestion) {

        if (addQuestion == null) throw new NullPointerException("addQuestion is null");

        return Questions.builder()
                    .description(addQuestion.getDescription())
                    .is_multiple_choice(addQuestion.getIs_multiple_choice())
                    .contentType(addQuestion.getContentType())
                    .content(addQuestion.getContent())
                    .created_by(addQuestion.getCreated_by())
                    .is_public(addQuestion.getIs_public())
                    .build();
    }

    public static QuestionExternal mapToQuestionExternal(Questions question) {

        if (question == null) throw new NullPointerException("question is null");

        QuestionExternal questionDto = QuestionExternal.builder()
                                    .question_id(question.getQuestion_id())
                                    .description(question.getDescription())
                                    .is_multiple_choice(question.is_multiple_choice())
                                    .contentType(question.getContentType())
                                    .content(question.getContent())
                                    .created_by(question.getCreated_by())
                                    .created_at(question.getCreated_at())
                                    .is_public(question.is_public())
                                    .build();

        if (question.getParagraph() != null) {
            questionDto.setParagraph_id(question.getParagraph().getParagraph_id());
        }

        return questionDto;
    }

    public static Questions mapToQuestionExternal(QuestionExternal updateQuestion, Questions question) {

        if (updateQuestion == null) throw new NullPointerException("updateQuestion is null");
        if (question == null) throw new NullPointerException("question is null");

        question.setQuestion_id(updateQuestion.getQuestion_id());
        question.setDescription(updateQuestion.getDescription());
        question.set_multiple_choice(updateQuestion.getIs_multiple_choice());
        question.setContentType(question.getContentType());
        question.setContent(updateQuestion.getContent());
        question.set_public(updateQuestion.getIs_public());

        return question;
    }

    public static QuestionIncludeAnswerDto mapToQuestionIncludeAnswersDto(Questions question) {

        if (question == null) throw new NullPointerException("question is null");

        QuestionIncludeAnswerDto questionDto = QuestionIncludeAnswerDto.builder()
                                    .question_id(question.getQuestion_id())
                                    .description(question.getDescription())
                                    .is_multiple_choice(question.is_multiple_choice())
                                    .contentType(question.getContentType())
                                    .content(question.getContent())
                                    .created_by(question.getCreated_by())
                                    .created_at(question.getCreated_at())
                                    .is_public(question.is_public())
                                    .answers(question.getAnswers().stream().map(AnswerMapper::mapToAnswerDto).collect(Collectors.toList()))
                                    .build();

        return questionDto;
    }
}
