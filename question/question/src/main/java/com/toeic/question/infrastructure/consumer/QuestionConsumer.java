package com.toeic.question.infrastructure.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.toeic.question.application.service.IQuestionService;
import com.toeic.question.domain.enums.EventType;
import com.toeic.question.domain.model.external.QuestionExternal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionConsumer {
    private final IQuestionService _questionService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "question", groupId = "demoGroup")
    public void listenJson(QuestionExternal record, @Header(KafkaHeaders.CORRELATION_ID) String correlationId) {
        try {
            var eventType = EventType.fromString(record.getEvent_type());

            String responseMessage;
            switch (eventType) {
                case CREATE:
                    var newQuestion = _questionService.addQuestionKafka(record);
                    log.info("CREATE: {}", newQuestion);
                    responseMessage = "Question created successfully: " + newQuestion.getQuestion_id();
                    break;
                case UPDATE:
                    var updateQuestion = _questionService.updateQuestionKafka(record);
                    log.info("UPDATE: {}", record);
                    responseMessage = "Question updated successfully" + updateQuestion.getQuestion_id();
                    break;
                case DELETE:
                    _questionService.deleteQuestion(record.getQuestion_id());
                    log.info("DELETE: {}", record);
                    responseMessage = "Question deleted successfully";
                    break;
                default:
                    responseMessage = "Unknown event";
            }

            kafkaTemplate.send("question-response", correlationId + "::" + responseMessage);
        } catch (Exception e) {
            kafkaTemplate.send("question-response", correlationId + "::Error processing event");
        }
    }
}
