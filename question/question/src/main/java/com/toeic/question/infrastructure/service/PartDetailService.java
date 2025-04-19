package com.toeic.question.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toeic.question.application.dto.PartDetailDto;
import com.toeic.question.application.dto.relation.AddPartDetailDto;
import com.toeic.question.application.dto.relation.DeletePartDetailDto;
import com.toeic.question.application.mapper.PartDetailMapper;
import com.toeic.question.application.service.IPartDetailService;
import com.toeic.question.domain.exception.BadRequestException;
import com.toeic.question.domain.exception.NotFoundException;
import com.toeic.question.domain.model.external.PartDetailExternal;
import com.toeic.question.domain.model.internal.Paragraphs;
import com.toeic.question.domain.model.internal.PartDetails;
import com.toeic.question.domain.model.internal.Parts;
import com.toeic.question.domain.model.internal.Questions;
import com.toeic.question.domain.model.relation.AddPartDetailExternal;
import com.toeic.question.domain.model.relation.DeletePartDetailExternal;
import com.toeic.question.domain.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class PartDetailService implements IPartDetailService {

    private final IPartRepository _partRepository;
    private final IQuestionRepository _questionRepository;
    private final IParagraphRepository _paragraphRepository;
    private final IPartDetailRepository _partDetailRepository;

    @SuppressWarnings("null")
    @Override
    @Transactional
    public void addPartDetail(AddPartDetailDto request) {

        if (request == null)
            throw new BadRequestException("request is null");

        Parts part = _partRepository.findById(request.getPart_id())
                .orElseThrow(() -> new NotFoundException("Part not found!"));

        PartDetails partDetails = null;

        if (request.getParagraph_id() > 0) {

            Paragraphs paragraph = _paragraphRepository.findById(request.getParagraph_id())
                    .orElseThrow(() -> new NotFoundException("Paragraph not found!"));

            var findPartDetail = _partDetailRepository.findPartDetailsWithParagraph(request.getPart_id(),
                    request.getParagraph_id());

            if (findPartDetail.isPresent())
                throw new BadRequestException("Paragraph is added in this part!");

            partDetails = PartDetailMapper.mapToPartDetailWithParagraph(part, paragraph);
        }

        else if (request.getQuestion_id() > 0) {

            Questions question = _questionRepository.findById(request.getQuestion_id())
                    .orElseThrow(() -> new NotFoundException("Question not found!"));

            var findPartDetail = _partDetailRepository.findPartDetailsWithQuestion(request.getPart_id(),
                    request.getQuestion_id());

            if (findPartDetail.isPresent())
                throw new BadRequestException("Question is added in this part!");

            partDetails = PartDetailMapper.mapToPartDetailWithQuestion(part, question);
        }

        partDetails.setOrder_number(request.getOrder_number());

        _partDetailRepository.save(partDetails);
    }

    @SuppressWarnings("null")
    @Override
    @Transactional
    public void deletePartDetail(DeletePartDetailDto request) {

        PartDetails findPartDetail = null;

        if (request.getParagraph_id() > 0) {
            findPartDetail = _partDetailRepository
                    .findPartDetailsWithParagraph(request.getPart_id(), request.getParagraph_id())
                    .orElseThrow(() -> new NotFoundException("Paragraph is not added in this part!"));
        }

        else if (request.getQuestion_id() > 0) {
            findPartDetail = _partDetailRepository
                    .findPartDetailsWithQuestion(request.getPart_id(), request.getQuestion_id())
                    .orElseThrow(() -> new NotFoundException("Question is not added in this part!"));
        }

        _partDetailRepository.delete(findPartDetail);
    }

    @Override
    public List<PartDetailDto> getPartDetails() {
        var partDetails = _partDetailRepository.findAllPartDetails();
        var partDetailDtos = partDetails.stream().map(PartDetailMapper::mapToPartDetailDto)
                .collect(Collectors.toList());
        return partDetailDtos;
    }

    @SuppressWarnings("null")
    @Override
    @Transactional
    public PartDetailExternal addPartDetailKafka(AddPartDetailExternal request) {
        if (request == null)
            throw new BadRequestException("request is null");

        Parts part = _partRepository.findById(request.getPart_id())
                .orElseThrow(() -> new NotFoundException("Part not found!"));

        PartDetails partDetails = null;

        if (request.getParagraph_id() > 0) {

            Paragraphs paragraph = _paragraphRepository.findById(request.getParagraph_id())
                    .orElseThrow(() -> new NotFoundException("Paragraph not found!"));

            var findPartDetail = _partDetailRepository.findPartDetailsWithParagraph(request.getPart_id(),
                    request.getParagraph_id());

            if (findPartDetail.isPresent())
                throw new BadRequestException("Paragraph is added in this part!");

            partDetails = PartDetailMapper.mapToPartDetailWithParagraph(part, paragraph);
        }

        else if (request.getQuestion_id() > 0) {

            Questions question = _questionRepository.findById(request.getQuestion_id())
                    .orElseThrow(() -> new NotFoundException("Question not found!"));

            var findPartDetail = _partDetailRepository.findPartDetailsWithQuestion(request.getPart_id(),
                    request.getQuestion_id());

            if (findPartDetail.isPresent())
                throw new BadRequestException("Question is added in this part!");

            partDetails = PartDetailMapper.mapToPartDetailWithQuestion(part, question);
        }

        partDetails.setOrder_number(request.getOrder_number());

        var savedPartDetail = _partDetailRepository.save(partDetails);

        return PartDetailMapper.mapToPartDetailExternal(savedPartDetail);
    }

    @SuppressWarnings("null")
    @Override
    @Transactional
    public void deletePartDetailKafka(DeletePartDetailExternal request) {
        PartDetails findPartDetail = null;

        if (request.getParagraph_id() > 0) {
            findPartDetail = _partDetailRepository
                    .findPartDetailsWithParagraph(request.getPart_id(), request.getParagraph_id())
                    .orElseThrow(() -> new NotFoundException("Paragraph is not added in this part!"));
        }

        else if (request.getQuestion_id() > 0) {
            findPartDetail = _partDetailRepository
                    .findPartDetailsWithQuestion(request.getPart_id(), request.getQuestion_id())
                    .orElseThrow(() -> new NotFoundException("Question is not added in this part!"));
        }

        _partDetailRepository.delete(findPartDetail);
    }

    @Override
    public List<PartDetailDto> getPartDetailByPartId(int part_id) {
        var partDetails = _partDetailRepository.findPartDetailsByPartId(part_id);
        var partDetailDtos = partDetails.stream().map(PartDetailMapper::mapToPartDetailDto)
                .collect(Collectors.toList());
        return partDetailDtos;
    }

}
