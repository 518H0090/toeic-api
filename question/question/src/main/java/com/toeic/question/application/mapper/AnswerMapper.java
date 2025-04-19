package com.toeic.question.application.mapper;

import com.toeic.question.application.dto.AnswerDto;
import com.toeic.question.application.dto.add.AddAnswerDto;
import com.toeic.question.application.dto.update.UpdateAnswerDto;
import com.toeic.question.domain.model.external.AnswerExternal;
import com.toeic.question.domain.model.internal.Answers;

public class AnswerMapper {

    public static AnswerDto mapToAnswerDto(Answers answer) {

        if (answer == null)
            throw new NullPointerException("answer is null");

        return AnswerDto.builder()
                .answer_id(answer.getAnswer_id())
                .question_id(answer.getQuestion().getQuestion_id())
                .text(answer.getText())
                .is_correct(answer.is_correct())
                .order_number(answer.getOrder_number())
                .created_at(answer.getCreated_at())
                .build();
    }

    public static Answers mapToAnswerFromAddDto(AddAnswerDto addAnswerDto) {

        if (addAnswerDto == null)
            throw new NullPointerException("addAnswerDto is null");

        return Answers.builder()
                .text(addAnswerDto.getText())
                .is_correct(addAnswerDto.getIs_correct())
                .build();
    }

    public static Answers mapToAnswer(UpdateAnswerDto updateAnswerDto, Answers answer) {

        if (updateAnswerDto == null)
            throw new NullPointerException("updateAnswerDto is null");
        if (answer == null)
            throw new NullPointerException("answer is null");

        answer.setAnswer_id(updateAnswerDto.getAnswer_id());
        answer.setText(updateAnswerDto.getText());
        answer.set_correct(updateAnswerDto.getIs_correct());
        answer.setOrder_number(updateAnswerDto.getOrder_number());

        return answer;
    }

    public static Answers mapToAnswerExternal(AnswerExternal addAnswer) {

        if (addAnswer == null)
            throw new NullPointerException("addAnswer is null");

        return Answers.builder()
                .text(addAnswer.getText())
                .is_correct(addAnswer.getIs_correct())
                .build();
    }

    public static AnswerExternal mapToAnswerExternal(Answers answer) {

        if (answer == null)
            throw new NullPointerException("answer is null");

        return AnswerExternal.builder()
                .answer_id(answer.getAnswer_id())
                .question_id(answer.getQuestion().getQuestion_id())
                .text(answer.getText())
                .is_correct(answer.is_correct())
                .order_number(answer.getOrder_number())
                .created_at(answer.getCreated_at())
                .build();
    }

    public static Answers mapToAnswerExternal(AnswerExternal updateAnswer, Answers answer) {

        if (updateAnswer == null)
            throw new NullPointerException("updateAnswer is null");
        if (answer == null)
            throw new NullPointerException("answer is null");

        answer.setAnswer_id(updateAnswer.getAnswer_id());
        answer.setText(updateAnswer.getText());
        answer.set_correct(updateAnswer.getIs_correct());
        answer.setOrder_number(updateAnswer.getOrder_number());

        return answer;
    }
}
