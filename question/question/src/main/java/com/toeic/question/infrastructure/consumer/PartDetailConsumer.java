package com.toeic.question.infrastructure.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.toeic.question.application.service.IPartDetailService;
import com.toeic.question.domain.model.relation.AddPartDetailExternal;
import com.toeic.question.domain.model.relation.DeletePartDetailExternal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartDetailConsumer {
    private final IPartDetailService _partDetailService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "part-detail", groupId = "demoGroup-add")
    public void listenJsonAdd(AddPartDetailExternal record, @Header(KafkaHeaders.CORRELATION_ID) String correlationId) {
        try {
            var newPartDetail = _partDetailService.addPartDetailKafka(record);
            log.info("CREATE: {}", newPartDetail);
            String responseMessage = "Part detail created successfully: " + newPartDetail;
            kafkaTemplate.send("part-detail-response", correlationId + "::" + responseMessage);

        } catch (Exception e) {
            kafkaTemplate.send("part-detail-response", correlationId + "::Error processing event");
        }
    }

    @KafkaListener(topics = "part-detail", groupId = "demoGroup-delete")
    public void listenJsonDelete(DeletePartDetailExternal record,
            @Header(KafkaHeaders.CORRELATION_ID) String correlationId) {
        try {
            _partDetailService.deletePartDetailKafka(record);
            log.info("DELETE: {}", record);
            String responseMessage = "Part detail deleted successfully";
            kafkaTemplate.send("part-detail-response", correlationId + "::" + responseMessage);

        } catch (Exception e) {
            kafkaTemplate.send("part-detail-response", correlationId + "::Error processing event");
        }
    }
}
