package com.toeic.question.infrastructure.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.kafka.support.KafkaHeaders;

import com.toeic.question.application.service.IExamService;
import com.toeic.question.domain.enums.EventType;
import com.toeic.question.domain.model.external.ExamExternal;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExamConsumer {

    private final IExamService _examService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "exam", groupId = "demoGroup")
    public void listenJson(ExamExternal record, @Header(KafkaHeaders.CORRELATION_ID) String correlationId) {
        try {
            var eventType = EventType.fromString(record.getEvent_type());

            String responseMessage;
            switch (eventType) {
                case CREATE:
                    var newExam = _examService.addExamKafka(record);
                    log.info("CREATE: {}", newExam);
                    responseMessage = "Exam created successfully: " + newExam.getExam_name();
                    break;
                case UPDATE:
                    var updateExam = _examService.updateExamKafka(record);
                    log.info("UPDATE: {}", record);
                    responseMessage = "Exam updated successfully" + updateExam.getExam_name();
                    break;
                case DELETE:
                    _examService.deleteExam(record.getExam_id());
                    log.info("DELETE: {}", record);
                    responseMessage = "Exam deleted successfully";
                    break;
                default:
                    responseMessage = "Unknown event";
            }

            kafkaTemplate.send("exam-response", correlationId + "::" + responseMessage);
        } catch (Exception e) {
            kafkaTemplate.send("exam-response", correlationId + "::Error processing event");
        }
    }
}
