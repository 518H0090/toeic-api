package com.toeic.user.adapter.controller.external;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toeic.user.domain.model.external.QuestionExternal;
import com.toeic.user.domain.model.external.add.AddQuestionExternal;
import com.toeic.user.domain.model.external.update.UpdateQuestionExternal;
import com.toeic.user.infrastructure.stream.QuestionStream;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/external/questions")
@RequiredArgsConstructor
public class QuestionStreamController {
    private final QuestionStream _questionStream;

    @GetMapping
    public Flux<QuestionExternal> fetchQuestions() {
        return _questionStream.getQuestions();
    }

     @GetMapping("fetch/{id}")
    public Mono<QuestionExternal> FetchQuestionById(@PathVariable("id") int questionId) {
        return _questionStream.getQuestionById(questionId);
    }

    @PostMapping("create")
    public ResponseEntity<?> createQuestionPublishers(@RequestBody @Valid AddQuestionExternal request) {
        String message = _questionStream.createQuestionPublishers(request);
        return ResponseEntity.ok(message);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateQuestionPublishers(@RequestBody @Valid UpdateQuestionExternal request) {
        String message = _questionStream.updateQuestionPublishers(request);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteQuestionPublishers(@PathVariable("id") Integer questionId) {
        String message = _questionStream.deleteQuestionPublishers(questionId);
        return ResponseEntity.ok(message);
    }
}
