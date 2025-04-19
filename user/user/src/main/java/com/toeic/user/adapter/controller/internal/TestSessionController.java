package com.toeic.user.adapter.controller.internal;

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

import com.toeic.user.application.dto.add.AddTestSessionDto;
import com.toeic.user.application.dto.update.UpdateTestSessionDto;
import com.toeic.user.application.service.ITestSessionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("test-sessions")
@RequiredArgsConstructor
public class TestSessionController {
    private final ITestSessionService _testSessionService;

    @PostMapping("create")
    public ResponseEntity<?> createTestSession(@RequestBody @Valid AddTestSessionDto request) {
        var message = _testSessionService.addTestSession(request);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<?> fetchTestSessions() {
        return ResponseEntity.ok(_testSessionService.getTestSessions());
    }

     @GetMapping("fetch/{id}")
    public ResponseEntity<?> fetchTestSessionById(@PathVariable("id") int testSessionId) {
        return ResponseEntity.ok(_testSessionService.getTestSessionById(testSessionId));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateTestSession(@RequestBody @Valid UpdateTestSessionDto request) {
        _testSessionService.updateTestSession(request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteTestSession(@PathVariable("id") Integer testSessionId) {
        _testSessionService.deleteTestSession(testSessionId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
