package com.toeic.question.application.mapper;

import java.util.stream.Collectors;

import com.toeic.question.application.dto.ParagraphDto;
import com.toeic.question.application.dto.add.AddParagraphDto;
import com.toeic.question.application.dto.relation.ParagraphIncludeQuestionDto;
import com.toeic.question.application.dto.update.UpdateParagraphDto;
import com.toeic.question.domain.model.external.ParagraphExternal;
import com.toeic.question.domain.model.internal.Paragraphs;

public class ParagraphMapper {

    public static ParagraphDto mapToParagraphDto(Paragraphs paragraph) {

        if (paragraph == null) throw new NullPointerException("paragraph is null");

        return ParagraphDto.builder()
                .paragraph_id(paragraph.getParagraph_id())
                .content(paragraph.getContent())
                .created_by(paragraph.getCreated_by())
                .created_at(paragraph.getCreated_at())
                .is_public(paragraph.is_public())
                .build();
    }

    public static Paragraphs mapToParagraphFromAddDto(AddParagraphDto addParagraphDto) {

        if (addParagraphDto == null) throw new NullPointerException("addParagraphDto is null");

        return Paragraphs.builder()
                    .content(addParagraphDto.getContent())
                    .created_by(addParagraphDto.getCreated_by())
                    .is_public(addParagraphDto.getIs_public())
                    .build();
    }

    public static Paragraphs mapToParagraph(UpdateParagraphDto updateParagraphDto, Paragraphs paragraph) {

        if (updateParagraphDto == null) throw new NullPointerException("updateParagraphDto is null");
        if (paragraph == null) throw new NullPointerException("paragraph is null");

        paragraph.setParagraph_id(updateParagraphDto.getParagraph_id());
        paragraph.setContent(updateParagraphDto.getContent());
        paragraph.set_public(updateParagraphDto.getIs_public());

        return paragraph;
    }

    public static ParagraphExternal mapToParagraphExternal(Paragraphs paragraph) {

        if (paragraph == null) throw new NullPointerException("paragraph is null");

        return ParagraphExternal.builder()
                .paragraph_id(paragraph.getParagraph_id())
                .content(paragraph.getContent())
                .created_by(paragraph.getCreated_by())
                .created_at(paragraph.getCreated_at())
                .is_public(paragraph.is_public())
                .build();
    }

    public static Paragraphs mapToParagraphExternal(ParagraphExternal addParagraph) {

        if (addParagraph == null) throw new NullPointerException("addParagraph is null");

        return Paragraphs.builder()
                    .content(addParagraph.getContent())
                    .created_by(addParagraph.getCreated_by())
                    .is_public(addParagraph.getIs_public())
                    .build();
    }

    public static Paragraphs mapToParagraphExternal(ParagraphExternal updateParagraph, Paragraphs paragraph) {

        if (updateParagraph == null) throw new NullPointerException("updateParagraph is null");
        if (paragraph == null) throw new NullPointerException("paragraph is null");

        paragraph.setParagraph_id(updateParagraph.getParagraph_id());
        paragraph.setContent(updateParagraph.getContent());
        paragraph.set_public(updateParagraph.getIs_public());

        return paragraph;
    }

    public static ParagraphIncludeQuestionDto mapToParagraphIncludeQuestionsDto(Paragraphs paragraph) {

        if (paragraph == null) throw new NullPointerException("paragraph is null");

        return ParagraphIncludeQuestionDto.builder()
                .paragraph_id(paragraph.getParagraph_id())
                .content(paragraph.getContent())
                .created_by(paragraph.getCreated_by())
                .created_at(paragraph.getCreated_at())
                .is_public(paragraph.is_public())
                .questions(paragraph.getQuestions().stream().map(QuestionMapper::mapToQuestionIncludeAnswersDto).collect(Collectors.toList()))
                .build();
    }
}
