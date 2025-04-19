package com.toeic.question.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toeic.question.application.dto.relation.AddPartDetailDto;
import com.toeic.question.application.dto.relation.DeletePartDetailDto;
import com.toeic.question.application.service.IPartDetailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/part-details")
@RequiredArgsConstructor
public class PartDetailController {

    private final IPartDetailService _partDetailService;

    @PostMapping("add")
    public ResponseEntity<?> AddPartDetail(@RequestBody @Valid AddPartDetailDto request) {
        _partDetailService.addPartDetail(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> DeletePartDetail(@RequestBody @Valid DeletePartDetailDto request) {
        _partDetailService.deletePartDetail(request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> GetPartDetails() {
        var partDetails = _partDetailService.getPartDetails();
        return new ResponseEntity<>(partDetails, HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> GetPartDetailById(@PathVariable("id") int partId) {
        var partDetail = _partDetailService.getPartDetailByPartId(partId);
        return new ResponseEntity<>(partDetail, HttpStatus.OK);
    }
}
