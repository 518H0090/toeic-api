package com.toeic.question.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toeic.question.application.dto.PartDto;
import com.toeic.question.application.dto.add.AddPartDto;
import com.toeic.question.application.dto.update.UpdatePartDto;
import com.toeic.question.application.mapper.PartMapper;
import com.toeic.question.application.service.IPartService;
import com.toeic.question.domain.exception.BadRequestException;
import com.toeic.question.domain.exception.NotFoundException;
import com.toeic.question.domain.model.external.PartExternal;
import com.toeic.question.domain.model.internal.Parts;
import com.toeic.question.domain.repository.IExamRepository;
import com.toeic.question.domain.repository.IPartRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class PartService implements IPartService {

    private final IPartRepository _partRepository;
    private final IExamRepository _examRepository;

    @Override
    @Transactional
    public PartDto addPart(AddPartDto addPartDto) {
        if (addPartDto == null)
            throw new BadRequestException("addPartDto is null");

        var findExam = _examRepository.findById(addPartDto.getExam_id())
                .orElseThrow(() -> new NotFoundException("Cannot find exam with id: " + addPartDto.getExam_id()));

        Parts part = PartMapper.mapToPartFromAddDto(addPartDto);
        part.setExam(findExam);
        Parts savedExam = _partRepository.save(part);

        return PartMapper.mapToPartDto(savedExam);
    }

    @Override
    @Transactional
    public void updatePart(UpdatePartDto updatePartDto) {
        var findPart = _partRepository.findById(updatePartDto.getPart_id())
                .orElseThrow(() -> new NotFoundException("Cannot find part with id: " + updatePartDto.getPart_id()));

        var findExam = _examRepository.findById(updatePartDto.getExam_id())
                .orElseThrow(() -> new NotFoundException("Cannot find exam with id: " + updatePartDto.getExam_id()));

        var updatedPart = PartMapper.mapToPart(updatePartDto, findPart);
        updatedPart.setExam(findExam);

        _partRepository.save(updatedPart);
    }

    @Override
    public PartDto getPartById(int id) {
        var findPart = _partRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find part with id: " + id));
        return PartMapper.mapToPartDto(findPart);
    }

    @Override
    public List<PartDto> getParts() {
        var allParts = _partRepository.findAll();
        return allParts.stream().map(PartMapper::mapToPartDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePart(int id) {
        var findPart = _partRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find part with id: " + id));
        _partRepository.delete(findPart);
    }

    @Override
    @Transactional
    public PartExternal addPartKafka(PartExternal addPart) {
        if (addPart == null)
            throw new BadRequestException("addPart is null");

        var findExam = _examRepository.findById(addPart.getExam_id())
                .orElseThrow(() -> new NotFoundException("Cannot find exam with id: " + addPart.getExam_id()));

        Parts paragraphs = PartMapper.mapToPartExternal(addPart);
        paragraphs.setExam(findExam);
        Parts savedParagraph = _partRepository.save(paragraphs);

        return PartMapper.mapToPartExternal(savedParagraph);
    }

    @Override
    public PartExternal updatePartKafka(PartExternal updatePart) {
        var findPart = _partRepository.findById(updatePart.getPart_id())
                .orElseThrow(() -> new NotFoundException(
                        "Cannot find part with id: " + updatePart.getPart_id()));

        var findExam = _examRepository.findById(updatePart.getExam_id())
                .orElseThrow(() -> new NotFoundException("Cannot find exam with id: " + updatePart.getExam_id()));

        var updatedPart = PartMapper.mapToPartExternal(updatePart, findPart);
        updatedPart.setExam(findExam);

        Parts savedPart = _partRepository.save(updatedPart);

        return PartMapper.mapToPartExternal(savedPart);
    }

}
