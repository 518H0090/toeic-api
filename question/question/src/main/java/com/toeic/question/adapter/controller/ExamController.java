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

import com.toeic.question.application.dto.add.AddExamDto;
import com.toeic.question.application.dto.update.UpdateExamDto;
import com.toeic.question.application.service.IExamService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final IExamService _examService;

    @GetMapping
    public ResponseEntity<?> GetExams() {
        var exams = _examService.getExams();
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> GetExamById(@PathVariable("id") int examId) {
        var exam = _examService.getExamById(examId);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    @GetMapping("get-include-part/{id}")
    public ResponseEntity<?> GetExamIncludePartById(@PathVariable("id") int examId) {
        var exam = _examService.getExamIncludePartById(examId);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> AddExam(@RequestBody @Valid AddExamDto request) {
        var addedExam = _examService.addExam(request);
        return new ResponseEntity<>(addedExam, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> UpdateExam(@RequestBody @Valid UpdateExamDto request) {
        _examService.updateExam(request);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> DeleteExam(@PathVariable("id") Integer examId) {
        _examService.deleteExam(examId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
