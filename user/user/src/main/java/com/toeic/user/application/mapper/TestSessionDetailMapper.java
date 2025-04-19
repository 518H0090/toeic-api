package com.toeic.user.application.mapper;

import com.toeic.user.application.dto.TestSessionDetailDto;
import com.toeic.user.application.dto.add.AddTestSessionDetailDto;
import com.toeic.user.domain.model.external.QuestionExternal;
import com.toeic.user.domain.model.internal.TestSessionDetails;

public class TestSessionDetailMapper {

    public static TestSessionDetails mapToTestSessionDetailFromAddDto(AddTestSessionDetailDto addTestSessionDetailDto) {
        if (addTestSessionDetailDto == null)
            throw new NullPointerException("addTestSessionDetailDto is null");

        return TestSessionDetails.builder()
                .selected_answer(addTestSessionDetailDto.getSelected_answer())
                .is_correct(addTestSessionDetailDto.getIs_correct())
                .build();
    }

    public static TestSessionDetailDto mapToTestSessionDetailDto(TestSessionDetails savedTestSession,
            QuestionExternal findQuestion) {

        if (savedTestSession == null)
            throw new NullPointerException("savedTestSession is null");

        return TestSessionDetailDto.builder()
                .test_session_detail_id(savedTestSession.getTest_session_detail_id())
                .test_session(TestSessionMapper.mapToTestSessionDto(savedTestSession.getTest_session()))
                .question(findQuestion)
                .selected_answer(savedTestSession.getSelected_answer())
                .is_correct(savedTestSession.is_correct())
                .created_at(savedTestSession.getCreated_at())
                .build();
    }

    public static TestSessionDetailDto mapToTestSessionDetailDto(TestSessionDetails savedTestSession) {

        if (savedTestSession == null)
            throw new NullPointerException("savedTestSession is null");

        return TestSessionDetailDto.builder()
                .test_session_detail_id(savedTestSession.getTest_session_detail_id())
                .test_session(TestSessionMapper.mapToTestSessionDto(savedTestSession.getTest_session()))
                .selected_answer(savedTestSession.getSelected_answer())
                .is_correct(savedTestSession.is_correct())
                .created_at(savedTestSession.getCreated_at())
                .build();

    }
}
