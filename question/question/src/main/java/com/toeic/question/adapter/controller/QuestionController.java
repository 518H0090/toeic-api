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

import com.toeic.question.application.dto.add.AddQuestionDto;
import com.toeic.question.application.dto.update.UpdateQuestionDto;
import com.toeic.question.application.service.IQuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final IQuestionService _questionService;

    @GetMapping
    public ResponseEntity<?> GetQuestions() {
        var questions = _questionService.getQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> GetQuestionById(@PathVariable("id") int questionId) {
        var question = _questionService.getQuestionById(questionId);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> AddQuestion(@RequestBody @Valid AddQuestionDto request) {
        var addedQuestion = _questionService.addQuestion(request);
        return new ResponseEntity<>(addedQuestion, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> UpdateQuestion(@RequestBody @Valid UpdateQuestionDto request) {
        _questionService.updateQuestion(request);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> DeleteQuestion(@PathVariable("id") Integer questionId) {
        _questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
