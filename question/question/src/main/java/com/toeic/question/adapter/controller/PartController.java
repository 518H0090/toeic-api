package com.toeic.question.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toeic.question.application.dto.add.AddPartDto;
import com.toeic.question.application.dto.update.UpdatePartDto;
import com.toeic.question.application.service.IPartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parts")
@RequiredArgsConstructor
public class PartController {

    private final IPartService _partService;

    @GetMapping
    public ResponseEntity<?> GetParts() {
        var parts = _partService.getParts();
        return new ResponseEntity<>(parts, HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> GetPartById(@PathVariable("id") int partId) {
        var part = _partService.getPartById(partId);
        return new ResponseEntity<>(part, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> AddPart(@RequestBody @Valid AddPartDto request) {
        var addedPart = _partService.addPart(request);
        return new ResponseEntity<>(addedPart, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> UpdatePart(@RequestBody @Valid UpdatePartDto request) {
        _partService.updatePart(request);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> DeletePart(@PathVariable("id") Integer partId) {
        _partService.deletePart(partId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
