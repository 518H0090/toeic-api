package com.toeic.user.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toeic.user.application.dto.TestSessionDetailDto;
import com.toeic.user.application.dto.add.AddTestSessionDetailDto;
import com.toeic.user.application.mapper.TestSessionDetailMapper;
import com.toeic.user.application.service.ITestSessionDetailService;
import com.toeic.user.domain.exception.BadRequestException;
import com.toeic.user.domain.exception.NotFoundException;
import com.toeic.user.domain.model.internal.TestSessionDetails;
import com.toeic.user.domain.model.internal.TestSessions;
import com.toeic.user.domain.repository.ITestSessionDetailRepository;
import com.toeic.user.domain.repository.ITestSessionRepository;
import com.toeic.user.infrastructure.stream.QuestionStream;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class TestSessionDetailService implements ITestSessionDetailService {

    private final ITestSessionDetailRepository _testSessionDetailRepository;
    private final QuestionStream _questionStream;
    private final ITestSessionRepository _testSessionRepository;

    @Override
    @Transactional
    public TestSessionDetailDto addTestSessionDetail(AddTestSessionDetailDto addTestSessionDetailDto) {
        if (addTestSessionDetailDto == null)
            throw new BadRequestException("addTestSessionDetailDto is null");

        var findTestSessionDetail = _testSessionDetailRepository.findPartDetailsWithQuestion(addTestSessionDetailDto.getQuestion_id(),
                addTestSessionDetailDto.getTest_session_id());

        if (findTestSessionDetail.isPresent())
            throw new BadRequestException("Question is added in this test session!");

        TestSessions findTestSession = _testSessionRepository.findById(addTestSessionDetailDto.getTest_session_id())
                .orElseThrow(() -> new NotFoundException(
                        "Cannot find test session with id: " + addTestSessionDetailDto.getTest_session_id()));

        var findQuestion = _questionStream.getQuestionById(addTestSessionDetailDto.getQuestion_id()).block();

        TestSessionDetails testSessionDetail = TestSessionDetailMapper
                .mapToTestSessionDetailFromAddDto(addTestSessionDetailDto);
        testSessionDetail.setQuestion_id(findQuestion.getQuestion_id());
        testSessionDetail.setTest_session(findTestSession);
        TestSessionDetails savedTestSession = _testSessionDetailRepository.save(testSessionDetail);

        return TestSessionDetailMapper.mapToTestSessionDetailDto(savedTestSession, findQuestion);
    }

    @Override
    public TestSessionDetailDto getTestSessionDetailById(int id) {
        TestSessionDetails findTestSessionDetail = _testSessionDetailRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cannot find test session detail with id: " + id));

        _testSessionRepository.findById(findTestSessionDetail.getTest_session().getTest_session_id())
                .orElseThrow(() -> new NotFoundException(
                        "Cannot find test session detail with id: "
                                + findTestSessionDetail.getTest_session().getTest_session_id()));

        var findQuestion = _questionStream.getQuestionById(findTestSessionDetail.getQuestion_id()).block();

        return TestSessionDetailMapper.mapToTestSessionDetailDto(findTestSessionDetail, findQuestion);
    }

    @Override
    public List<TestSessionDetailDto> getTestSessionDetails() {
        var allTestSessionDetails = _testSessionDetailRepository.findAll();
        return allTestSessionDetails.stream().map(testSessionDetail -> {
            var findQuestion = _questionStream.getQuestionById(testSessionDetail.getQuestion_id()).block();
            return TestSessionDetailMapper.mapToTestSessionDetailDto(testSessionDetail, findQuestion);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTestSessionDetail(int id) {
        var findTestSessionDetail = _testSessionDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find test session detail with id: " + id));
        _testSessionDetailRepository.delete(findTestSessionDetail);
    }
}
