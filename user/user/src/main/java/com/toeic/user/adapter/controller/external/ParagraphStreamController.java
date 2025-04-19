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

import com.toeic.user.domain.model.external.ParagraphExternal;
import com.toeic.user.domain.model.external.add.AddParagraphExternal;
import com.toeic.user.domain.model.external.update.UpdateParagraphExternal;
import com.toeic.user.infrastructure.stream.ParagraphStream;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/external/paragraphs")
@RequiredArgsConstructor
public class ParagraphStreamController {

    private final ParagraphStream _paragraphStream;

    @GetMapping
    public Flux<ParagraphExternal> fetchParagraphs() {
        return _paragraphStream.getParagraphs();
    }

    @GetMapping("fetch/{id}")
    public Mono<ParagraphExternal> FetchParagraphById(@PathVariable("id") int paragraphId) {
        return _paragraphStream.getParagraphById(paragraphId);
    }

    @PostMapping("create")
    public ResponseEntity<?> createParagraphPublishers(@RequestBody @Valid AddParagraphExternal request) {
        String message = _paragraphStream.createParagraphPublishers(request);
        return ResponseEntity.ok(message);
    }

     @PutMapping("update")
    public ResponseEntity<?> updateParagraphPublishers(@RequestBody @Valid UpdateParagraphExternal request) {
        String message = _paragraphStream.updateParagraphPublishers(request);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteParagraphPublishers(@PathVariable("id") Integer examId) {
        String message = _paragraphStream.deleteParagraphPublishers(examId);
        return ResponseEntity.ok(message);
    }
}
