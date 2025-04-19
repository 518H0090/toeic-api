package com.toeic.user.application.service;

import java.util.List;

import com.toeic.user.application.dto.TestSessionDto;
import com.toeic.user.application.dto.add.AddTestSessionDto;
import com.toeic.user.application.dto.relation.TestSessionPartsDto;
import com.toeic.user.application.dto.update.UpdateTestSessionDto;

public interface ITestSessionService {
    TestSessionDto addTestSession(AddTestSessionDto addTestSessionDto);
    void updateTestSession(UpdateTestSessionDto updateTestSessionDto);
    TestSessionPartsDto getTestSessionById(int id);
    List<TestSessionDto> getTestSessions();
    void deleteTestSession(int id);
}
