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

import com.toeic.question.application.dto.add.AddAnswerDto;
import com.toeic.question.application.dto.update.UpdateAnswerDto;
import com.toeic.question.application.service.IAnswerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final IAnswerService _answerService;

    @GetMapping
    public ResponseEntity<?> GetAnswers() {
        var answers = _answerService.getAnswers();
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> GetAnswerById(@PathVariable("id") int answerId) {
        var answer = _answerService.getAnswerById(answerId);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> AddAnswer(@RequestBody @Valid AddAnswerDto request) {
        var addedAnswer = _answerService.addAnswer(request);
        return new ResponseEntity<>(addedAnswer, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> UpdateAnswer(@RequestBody @Valid UpdateAnswerDto request) {
        _answerService.updateAnswer(request);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> DeleteAnswer(@PathVariable("id") Integer answerId) {
        _answerService.deleteAnswer(answerId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
