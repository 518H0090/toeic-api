package com.toeic.user.application.mapper;

import java.sql.Timestamp;

import com.toeic.user.application.dto.TestSessionDto;
import com.toeic.user.application.dto.add.AddTestSessionDto;
import com.toeic.user.application.dto.relation.TestSessionPartsDto;
import com.toeic.user.application.dto.update.UpdateTestSessionDto;
import com.toeic.user.domain.enums.SessionType;
import com.toeic.user.domain.model.external.ExamExternal;
import com.toeic.user.domain.model.external.relation.ExamIncludePartExternal;
import com.toeic.user.domain.model.internal.TestSessions;

public class TestSessionMapper {

    public static TestSessionDto mapToTestSessionDto(TestSessions testSession, ExamExternal exam) {

        if (testSession == null)
            throw new NullPointerException("testSession is null");

        return TestSessionDto.builder()
                .test_session_id(testSession.getTest_session_id())
                .user_id(testSession.getUser_id())
                .exam(exam)
                .start_time(testSession.getStart_time())
                .end_time(testSession.getEnd_time())
                .score(testSession.getScore())
                .status(testSession.getStatus())
                .created_at(testSession.getCreated_at())
                .build();
    }

    public static TestSessionPartsDto mapToTestSessionIncludePartDto(TestSessions testSession,
            ExamIncludePartExternal exam) {

        if (testSession == null)
            throw new NullPointerException("testSession is null");

        return TestSessionPartsDto.builder()
                .test_session_id(testSession.getTest_session_id())
                .user_id(testSession.getUser_id())
                .exam(exam)
                .start_time(testSession.getStart_time())
                .end_time(testSession.getEnd_time())
                .score(testSession.getScore())
                .status(testSession.getStatus())
                .created_at(testSession.getCreated_at())
                .build();
    }

    public static TestSessions mapToTestSessionFromAddDto(AddTestSessionDto addPartDto) {

        if (addPartDto == null)
            throw new NullPointerException("addPartDto is null");

        return TestSessions.builder()
                .user_id(addPartDto.getUser_id())
                .exam_id(addPartDto.getExam_id())
                .score(0)
                .status(SessionType.PROGRESS)
                .build();
    }

    public static TestSessions mapToTestSession(UpdateTestSessionDto updateTestSessionDto, TestSessions testSessions) {

        if (updateTestSessionDto == null)
            throw new NullPointerException("updateTestSessionDto is null");

        testSessions.setTest_session_id(updateTestSessionDto.getTest_session_id());
        testSessions.setEnd_time(new Timestamp(System.currentTimeMillis()));
        testSessions.setScore(updateTestSessionDto.getScore());
        testSessions.setStatus(updateTestSessionDto.getStatus());

        return testSessions;
    }

    public static TestSessionDto mapToTestSessionDto(TestSessions testSession) {

        if (testSession == null)
            throw new NullPointerException("testSession is null");

        return TestSessionDto.builder()
                .test_session_id(testSession.getTest_session_id())
                .user_id(testSession.getUser_id())
                .start_time(testSession.getStart_time())
                .end_time(testSession.getEnd_time())
                .score(testSession.getScore())
                .status(testSession.getStatus())
                .created_at(testSession.getCreated_at())
                .build();
    }
}
