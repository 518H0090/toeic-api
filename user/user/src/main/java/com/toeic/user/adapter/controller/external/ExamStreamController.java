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

import com.toeic.user.domain.model.external.ExamExternal;
import com.toeic.user.domain.model.external.add.AddExamExternal;
import com.toeic.user.domain.model.external.relation.ExamIncludePartExternal;
import com.toeic.user.domain.model.external.update.UpdateExamExternal;
import com.toeic.user.infrastructure.stream.ExamStream;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/external/exams")
@RequiredArgsConstructor
public class ExamStreamController {

    private final ExamStream examStream;

    @GetMapping
    public Flux<ExamExternal> fetchExams() {
        return examStream.getExams();
    }

    @GetMapping("fetch/{id}")
    public Mono<ExamExternal> FetchExamById(@PathVariable("id") int examId) {
        return examStream.getExamById(examId);
    }

    @GetMapping("fetch-include-part/{id}")
    public Mono<ExamIncludePartExternal> FetchExamIncludePartById(@PathVariable("id") int examId) {
        return examStream.getExamIncludePartById(examId);
    }

    @PostMapping("create")
    public ResponseEntity<?> createExamPublishers(@RequestBody @Valid AddExamExternal request) {
        String message = examStream.createExamPublishers(request);
        return ResponseEntity.ok(message);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateExamPublishers(@RequestBody @Valid UpdateExamExternal request) {
        String message = examStream.updateExamPublishers(request);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteExamPublishers(@PathVariable("id") Integer examId) {
        String message = examStream.deleteExamPublishers(examId);
        return ResponseEntity.ok(message);
    }

}
