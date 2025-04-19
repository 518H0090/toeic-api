package com.toeic.question.application.mapper;

import com.toeic.question.application.dto.PartDetailDto;
import com.toeic.question.domain.model.external.PartDetailExternal;
import com.toeic.question.domain.model.internal.Paragraphs;
import com.toeic.question.domain.model.internal.PartDetails;
import com.toeic.question.domain.model.internal.Parts;
import com.toeic.question.domain.model.internal.Questions;

public class PartDetailMapper {

    public static PartDetails mapToPartDetailWithParagraph(Parts part, Paragraphs paragraph) {
        return PartDetails.builder()
                    .part(part)
                    .paragraph(paragraph)
                    .build();
    }

    public static PartDetails mapToPartDetailWithQuestion(Parts part, Questions question) {
        return PartDetails.builder()
                    .part(part)
                    .question(question)
                    .build();
    }

    public static PartDetailDto mapToPartDetailDto(PartDetails partDetails) {
        if (partDetails == null) {
            return null;
        }

        PartDetailDto partDetailDto = PartDetailDto.builder()
                            .part_detail_id(partDetails.getPart_detail_id())
                            .part(PartMapper.mapToPartDto(partDetails.getPart()))
                            .order_number(partDetails.getOrder_number())
                            .created_at(partDetails.getCreated_at())
                            .build();

        if (partDetails.getParagraph() == null) {
            partDetailDto.setQuestion(QuestionMapper.mapToQuestionIncludeAnswersDto(partDetails.getQuestion()));
            partDetailDto.setParagraph(null); 
        }

        else if (partDetails.getQuestion() == null) {
            partDetailDto.setQuestion(null); 
            partDetailDto.setParagraph(ParagraphMapper.mapToParagraphIncludeQuestionsDto(partDetails.getParagraph())); 
        }

        return partDetailDto;
    }

    public static PartDetailExternal mapToPartDetailExternal(PartDetails partDetails) {
        if (partDetails == null) {
            return null;
        }

        PartDetailExternal partDetailExternal = PartDetailExternal.builder()
                            .part_detail_id(partDetails.getPart_detail_id())
                            .part(PartMapper.mapToPartExternal(partDetails.getPart()))
                            .order_number(partDetails.getOrder_number())
                            .created_at(partDetails.getCreated_at())
                            .build();

        if (partDetails.getParagraph() == null) {
            partDetailExternal.setQuestion(QuestionMapper.mapToQuestionExternal(partDetails.getQuestion()));
            partDetailExternal.setParagraph(null); 
        }

        else if (partDetails.getQuestion() == null) {
            partDetailExternal.setQuestion(null); 
            partDetailExternal.setParagraph(ParagraphMapper.mapToParagraphExternal(partDetails.getParagraph())); 
        }

        return partDetailExternal;
    }

    public static PartDetailDto mapToPartDetailQuestionsDto(PartDetails partDetails) {
        if (partDetails == null) {
            return null;
        }

        PartDetailDto partDetailDto = PartDetailDto.builder()
                            .part_detail_id(partDetails.getPart_detail_id())
                            .part(PartMapper.mapToPartDto(partDetails.getPart()))
                            .order_number(partDetails.getOrder_number())
                            .created_at(partDetails.getCreated_at())
                            .build();

        if (partDetails.getParagraph() == null) {
            partDetailDto.setQuestion(QuestionMapper.mapToQuestionIncludeAnswersDto(partDetails.getQuestion()));
            partDetailDto.setParagraph(null); 
        }

        else if (partDetails.getQuestion() == null) {
            partDetailDto.setQuestion(null); 
            partDetailDto.setParagraph(ParagraphMapper.mapToParagraphIncludeQuestionsDto(partDetails.getParagraph())); 
        }

        return partDetailDto;
    }
}
