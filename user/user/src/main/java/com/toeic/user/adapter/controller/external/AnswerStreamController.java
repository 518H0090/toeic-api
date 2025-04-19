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

import com.toeic.user.domain.model.external.AnswerExternal;
import com.toeic.user.domain.model.external.add.AddAnswerExternal;
import com.toeic.user.domain.model.external.update.UpdateAnswerExternal;
import com.toeic.user.infrastructure.stream.AnswerStream;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/external/answers")
@RequiredArgsConstructor
public class AnswerStreamController {

    private final AnswerStream _answerStream;

    @GetMapping
    public Flux<AnswerExternal> fetchAnswers() {
        return _answerStream.getAnswers();
    }

    @GetMapping("fetch/{id}")
    public Mono<AnswerExternal> FetchAnswerById(@PathVariable("id") int answerId) {
        return _answerStream.getAnswerById(answerId);
    }

    @PostMapping("create")
    public ResponseEntity<?> createAnswerPublishers(@RequestBody @Valid AddAnswerExternal request) {
        String message = _answerStream.createAnswerPublishers(request);
        return ResponseEntity.ok(message);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateAnswerPublishers(@RequestBody @Valid UpdateAnswerExternal request) {
        String message = _answerStream.updateAnswerPublishers(request);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteAnswerPublishers(@PathVariable("id") Integer answerId) {
        String message = _answerStream.deleteAnswerPublishers(answerId);
        return ResponseEntity.ok(message);
    }
}
