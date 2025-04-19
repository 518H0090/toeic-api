package com.toeic.question.infrastructure.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.toeic.question.application.service.IParagraphService;
import com.toeic.question.domain.enums.EventType;
import com.toeic.question.domain.model.external.ParagraphExternal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParagraphConsumer {
    private final IParagraphService _paragraphService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "paragraph", groupId = "demoGroup")
    public void listenJson(ParagraphExternal record, @Header(KafkaHeaders.CORRELATION_ID) String correlationId) {
        try {
            var eventType = EventType.fromString(record.getEvent_type());

            String responseMessage;
            switch (eventType) {
                case CREATE:
                    var newParagraph = _paragraphService.addParagraphKafka(record);
                    log.info("CREATE: {}", newParagraph);
                    responseMessage = "Paragraph created successfully: " + newParagraph.getParagraph_id();
                    break;
                case UPDATE:
                    var updateParagraph = _paragraphService.updateParagraphKafka(record);
                    log.info("UPDATE: {}", record);
                    responseMessage = "Paragraph updated successfully" + updateParagraph.getParagraph_id();
                    break;
                case DELETE:
                    _paragraphService.deleteParagraph(record.getParagraph_id());
                    log.info("DELETE: {}", record);
                    responseMessage = "Paragraph deleted successfully";
                    break;
                default:
                    responseMessage = "Unknown event";
            }

            kafkaTemplate.send("paragraph-response", correlationId + "::" + responseMessage);
        } catch (Exception e) {
            kafkaTemplate.send("paragraph-response", correlationId + "::Error processing event");
        }
    }
}
