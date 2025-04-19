package com.toeic.question.infrastructure.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.toeic.question.application.service.IAnswerService;
import com.toeic.question.domain.enums.EventType;
import com.toeic.question.domain.model.external.AnswerExternal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswerConsumer {
    private final IAnswerService _answerService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "answer", groupId = "demoGroup")
    public void listenJson(AnswerExternal record, @Header(KafkaHeaders.CORRELATION_ID) String correlationId) {
        try {
            var eventType = EventType.fromString(record.getEvent_type());

            String responseMessage;
            switch (eventType) {
                case CREATE:
                    var newAnswer = _answerService.addAnswerKafka(record);
                    log.info("CREATE: {}", newAnswer);
                    responseMessage = "Answer created successfully: " + newAnswer.getAnswer_id();
                    break;
                case UPDATE:
                    var updateAnswer = _answerService.updateAnswerKafka(record);
                    log.info("UPDATE: {}", record);
                    responseMessage = "Answer updated successfully" + updateAnswer.getAnswer_id();
                    break;
                case DELETE:
                    _answerService.deleteAnswer(record.getAnswer_id());
                    log.info("DELETE: {}", record);
                    responseMessage = "Answer deleted successfully";
                    break;
                default:
                    responseMessage = "Unknown event";
            }

            kafkaTemplate.send("answer-response", correlationId + "::" + responseMessage);
        } catch (Exception e) {
            kafkaTemplate.send("answer-response", correlationId + "::Error processing event");
        }
    }
}
