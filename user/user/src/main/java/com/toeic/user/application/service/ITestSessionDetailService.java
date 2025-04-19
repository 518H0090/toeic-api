package com.toeic.user.application.service;

import java.util.List;

import com.toeic.user.application.dto.TestSessionDetailDto;
import com.toeic.user.application.dto.add.AddTestSessionDetailDto;

public interface ITestSessionDetailService {
    TestSessionDetailDto addTestSessionDetail(AddTestSessionDetailDto addTestSessionDetailDto);
    TestSessionDetailDto getTestSessionDetailById(int id);
    List<TestSessionDetailDto> getTestSessionDetails();
    void deleteTestSessionDetail(int id);
}
