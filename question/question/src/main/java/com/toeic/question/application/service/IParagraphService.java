package com.toeic.question.application.service;

import java.util.List;

import com.toeic.question.application.dto.ParagraphDto;
import com.toeic.question.application.dto.add.AddParagraphDto;
import com.toeic.question.application.dto.update.UpdateParagraphDto;
import com.toeic.question.domain.model.external.ParagraphExternal;

public interface IParagraphService {
    ParagraphDto addParagraph(AddParagraphDto paragraphDto);

    void updateParagraph(UpdateParagraphDto updateParagraphDto);

    ParagraphDto getParagraphById(int id);

    List<ParagraphDto> getParagraphs();

    void deleteParagraph(int id);

    ParagraphExternal addParagraphKafka(ParagraphExternal addParagraph);

    ParagraphExternal updateParagraphKafka(ParagraphExternal updateParagraph);
}
