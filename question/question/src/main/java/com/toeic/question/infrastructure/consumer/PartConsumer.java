package com.toeic.question.infrastructure.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.toeic.question.application.service.IPartService;
import com.toeic.question.domain.enums.EventType;
import com.toeic.question.domain.model.external.PartExternal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartConsumer {
    private final IPartService _partService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "part", groupId = "demoGroup")
    public void listenJson(PartExternal record, @Header(KafkaHeaders.CORRELATION_ID) String correlationId) {
        try {
            var eventType = EventType.fromString(record.getEvent_type());

            String responseMessage;
            switch (eventType) {
                case CREATE:
                    var newPart = _partService.addPartKafka(record);
                    log.info("CREATE: {}", newPart);
                    responseMessage = "Part created successfully: " + newPart.getPart_id();
                    break;
                case UPDATE:
                    var updatePart = _partService.updatePartKafka(record);
                    log.info("UPDATE: {}", record);
                    responseMessage = "Part updated successfully" + updatePart.getPart_id();
                    break;
                case DELETE:
                    _partService.deletePart(record.getPart_id());
                    log.info("DELETE: {}", record);
                    responseMessage = "Part deleted successfully";
                    break;
                default:
                    responseMessage = "Unknown event";
            }

            kafkaTemplate.send("part-response", correlationId + "::" + responseMessage);
        } catch (Exception e) {
            kafkaTemplate.send("part-response", correlationId + "::Error processing event");
        }
    }
}
