package com.toeic.user.adapter.controller.external;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toeic.user.domain.model.external.PartDetailExternal;
import com.toeic.user.domain.model.external.relation.AddPartDetailExternal;
import com.toeic.user.domain.model.external.relation.DeletePartDetailExternal;
import com.toeic.user.infrastructure.stream.PartDetailStream;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/external/part-details")
@RequiredArgsConstructor
public class PartDetailStreamController {
    private final PartDetailStream _partDetailStream;

    @GetMapping
    public Flux<PartDetailExternal> fetchPartDetails() {
        return _partDetailStream.getPartDetails();
    }

    @GetMapping("fetch/{id}")
    public Flux<PartDetailExternal> FetchPartDetailById(@PathVariable("id") int partId) {
        return _partDetailStream.getPartDetailById(partId);
    }

    @PostMapping("create")
    public ResponseEntity<?> createPartDetailPublishers(@RequestBody @Valid AddPartDetailExternal request) {
        String message = _partDetailStream.createPartDetailPublishers(request);
        return ResponseEntity.ok(message);
    }
   
    @DeleteMapping("delete")
    public ResponseEntity<?> deletePartDetailPublishers(@RequestBody @Valid DeletePartDetailExternal examId) {
        String message = _partDetailStream.deletePartDetailPublishers(examId);
        return ResponseEntity.ok(message);
    }
}
