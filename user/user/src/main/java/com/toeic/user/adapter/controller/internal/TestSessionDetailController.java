package com.toeic.user.adapter.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toeic.user.application.dto.add.AddTestSessionDetailDto;
import com.toeic.user.application.service.ITestSessionDetailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("test-session-details")
@RequiredArgsConstructor
public class TestSessionDetailController {
    private final ITestSessionDetailService _testSessionDetailService;

    @PostMapping("create")
    public ResponseEntity<?> createTestSessionDetail(@RequestBody @Valid AddTestSessionDetailDto request) {
        var message = _testSessionDetailService.addTestSessionDetail(request);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<?> fetchTestSessionDetails() {
        return ResponseEntity.ok(_testSessionDetailService.getTestSessionDetails());
    }

    @GetMapping("fetch/{id}")
    public ResponseEntity<?> fetchTestSessionDetailById(@PathVariable("id") int testSessionId) {
        return ResponseEntity.ok(_testSessionDetailService.getTestSessionDetailById(testSessionId));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteTestSessionDetail(@PathVariable("id") Integer testSessionId) {
        _testSessionDetailService.deleteTestSessionDetail(testSessionId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
