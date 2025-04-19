package com.toeic.question.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toeic.question.application.dto.ParagraphDto;
import com.toeic.question.application.dto.add.AddParagraphDto;
import com.toeic.question.application.dto.update.UpdateParagraphDto;
import com.toeic.question.application.mapper.ParagraphMapper;
import com.toeic.question.application.service.IParagraphService;
import com.toeic.question.domain.exception.BadRequestException;
import com.toeic.question.domain.exception.NotFoundException;
import com.toeic.question.domain.model.external.ParagraphExternal;
import com.toeic.question.domain.model.internal.Paragraphs;
import com.toeic.question.domain.repository.IParagraphRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class ParagraphService implements IParagraphService {

    private final IParagraphRepository _paragraphRepository;

    @Override
    @Transactional
    public ParagraphDto addParagraph(AddParagraphDto paragraphDto) {
        if (paragraphDto == null)
            throw new BadRequestException("paragraphDto is null");

        Paragraphs paragraph = ParagraphMapper.mapToParagraphFromAddDto(paragraphDto);
        Paragraphs savedParagraph = _paragraphRepository.save(paragraph);

        return ParagraphMapper.mapToParagraphDto(savedParagraph);
    }

    @Override
    @Transactional
    public void updateParagraph(UpdateParagraphDto updateParagraphDto) {
        var findParagraph = _paragraphRepository.findById(updateParagraphDto.getParagraph_id())
                .orElseThrow(() -> new NotFoundException(
                        "Cannot find paragraph with id: " + updateParagraphDto.getParagraph_id()));

        var updatedParagraph = ParagraphMapper.mapToParagraph(updateParagraphDto, findParagraph);

        _paragraphRepository.save(updatedParagraph);
    }

    @Override
    public ParagraphDto getParagraphById(int id) {
        var findParagraph = _paragraphRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find paragraph with id: " + id));
        return ParagraphMapper.mapToParagraphDto(findParagraph);
    }

    @Override
    public List<ParagraphDto> getParagraphs() {
        var allParagraphs = _paragraphRepository.findAll();
        return allParagraphs.stream().map(ParagraphMapper::mapToParagraphDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteParagraph(int id) {
        var findParagraph = _paragraphRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find paragraph with id: " + id));
        _paragraphRepository.delete(findParagraph);
    }

    @Override
    @Transactional
    public ParagraphExternal addParagraphKafka(ParagraphExternal addParagraph) {
        if (addParagraph == null)
            throw new BadRequestException("addParagraph is null");

        Paragraphs paragraphs = ParagraphMapper.mapToParagraphExternal(addParagraph);
        Paragraphs savedParagraph = _paragraphRepository.save(paragraphs);

        return ParagraphMapper.mapToParagraphExternal(savedParagraph);
    }

    @Override
    @Transactional
    public ParagraphExternal updateParagraphKafka(ParagraphExternal updateParagraph) {
        var findParagraph = _paragraphRepository.findById(updateParagraph.getParagraph_id())
                .orElseThrow(() -> new NotFoundException(
                        "Cannot find paragraph with id: " + updateParagraph.getParagraph_id()));

        var updatedParagraph = ParagraphMapper.mapToParagraphExternal(updateParagraph, findParagraph);

        Paragraphs savedParagraph = _paragraphRepository.save(updatedParagraph);

        return ParagraphMapper.mapToParagraphExternal(savedParagraph);
    }

}
