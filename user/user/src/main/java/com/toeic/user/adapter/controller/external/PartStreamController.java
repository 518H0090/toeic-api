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

import com.toeic.user.domain.model.external.PartExternal;
import com.toeic.user.domain.model.external.add.AddPartExternal;
import com.toeic.user.domain.model.external.update.UpdatePartExternal;
import com.toeic.user.infrastructure.stream.PartStream;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/external/parts")
@RequiredArgsConstructor
public class PartStreamController {
    private final PartStream _partStream;

    @GetMapping
    public Flux<PartExternal> fetchParts() {
        return _partStream.getParts();
    }

    @GetMapping("fetch/{id}")
    public Mono<PartExternal> FetchPartById(@PathVariable("id") int partId) {
        return _partStream.getPartById(partId);
    }

    @PostMapping("create")
    public ResponseEntity<?> createPartPublishers(@RequestBody @Valid AddPartExternal request) {
        String message = _partStream.createPartPublishers(request);
        return ResponseEntity.ok(message);
    }

    @PutMapping("update")
    public ResponseEntity<?> updatePartPublishers(@RequestBody @Valid UpdatePartExternal request) {
        String message = _partStream.updatePartPublishers(request);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePartPublishers(@PathVariable("id") Integer examId) {
        String message = _partStream.deletePartPublishers(examId);
        return ResponseEntity.ok(message);
    }
}
