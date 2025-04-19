package com.toeic.user.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toeic.user.application.dto.TestSessionDto;
import com.toeic.user.application.dto.add.AddTestSessionDto;
import com.toeic.user.application.dto.relation.TestSessionPartsDto;
import com.toeic.user.application.dto.update.UpdateTestSessionDto;
import com.toeic.user.application.mapper.TestSessionDetailMapper;
import com.toeic.user.application.mapper.TestSessionMapper;
import com.toeic.user.application.service.ITestSessionService;
import com.toeic.user.domain.exception.BadRequestException;
import com.toeic.user.domain.exception.NotFoundException;
import com.toeic.user.domain.model.external.relation.ExamIncludePartExternal;
import com.toeic.user.domain.model.internal.TestSessions;
import com.toeic.user.domain.repository.ITestSessionRepository;
import com.toeic.user.infrastructure.stream.ExamStream;
import com.toeic.user.infrastructure.stream.QuestionStream;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class TestSessionService implements ITestSessionService{
    
    private final ITestSessionRepository _testSessionRepository;
    private final ExamStream _examStream;
    private final QuestionStream _questionStream;

    @Override
    @Transactional
    public TestSessionDto addTestSession(AddTestSessionDto addTestSessionDto) {
        if (addTestSessionDto == null)
            throw new BadRequestException("addTestSessionDto is null");

        var findExam = _examStream.getExamById(addTestSessionDto.getExam_id()).block();

        TestSessions testSession = TestSessionMapper.mapToTestSessionFromAddDto(addTestSessionDto);
        testSession.setExam_id(findExam.getExam_id());
        TestSessions savedTestSession = _testSessionRepository.save(testSession);

        return TestSessionMapper.mapToTestSessionDto(savedTestSession, findExam);
    }

    @Override
    @Transactional
    public void updateTestSession(UpdateTestSessionDto updateTestSessionDto) {
        var findTestSession = _testSessionRepository.findById(updateTestSessionDto.getTest_session_id())
                .orElseThrow(() -> new NotFoundException(
                        "Cannot find test session with id: " + updateTestSessionDto.getTest_session_id()));

        TestSessions testSession = TestSessionMapper.mapToTestSession(updateTestSessionDto, findTestSession);
        _testSessionRepository.save(testSession);
    }

    @Override
    public TestSessionPartsDto getTestSessionById(int id) {
        TestSessions findTestSession = _testSessionRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Cannot find test session with id: " + id));

        ExamIncludePartExternal findExam = _examStream.getExamIncludePartById(findTestSession.getExam_id()).block();

        var sessionDetails = findTestSession.getTest_session_details().stream().map(testSession -> {
            var findQuestion = _questionStream.getQuestionById(testSession.getQuestion_id()).block();
            return TestSessionDetailMapper.mapToTestSessionDetailDto(testSession, findQuestion);
        }).collect(Collectors.toList());

        var testSession = TestSessionMapper.mapToTestSessionIncludePartDto(findTestSession, findExam);

        testSession.setTest_session_details(sessionDetails);
        
        return testSession;
    }

    @Override
    public List<TestSessionDto> getTestSessions() {
        var allTestSessions = _testSessionRepository.findAll();
        return allTestSessions.stream().map(testSession -> {
            var findExam = _examStream.getExamById(testSession.getExam_id()).block();
            return TestSessionMapper.mapToTestSessionDto(testSession, findExam);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTestSession(int id) {
        var findTestSession = _testSessionRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find test session with id: " + id));
        _testSessionRepository.delete(findTestSession);
    }
}
