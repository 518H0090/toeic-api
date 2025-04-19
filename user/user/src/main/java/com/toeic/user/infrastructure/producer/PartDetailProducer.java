package com.toeic.user.infrastructure.producer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.toeic.user.domain.model.external.relation.AddPartDetailExternal;
import com.toeic.user.domain.model.external.relation.DeletePartDetailExternal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartDetailProducer {
    private final KafkaTemplate<String, AddPartDetailExternal> kafkaTemplateAdd;
    private final KafkaTemplate<String, DeletePartDetailExternal> kafkaTemplateDelete;
    private final ConcurrentHashMap<String, CompletableFuture<String>> pendingResponses = new ConcurrentHashMap<>();

    public CompletableFuture<String> sendAdd(AddPartDetailExternal partDetail) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<String> futureResponse = new CompletableFuture<>();
        pendingResponses.put(correlationId, futureResponse);

        Message<AddPartDetailExternal> message = MessageBuilder
                .withPayload(partDetail)
                .setHeader(KafkaHeaders.TOPIC, "part-detail")
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .build();

        kafkaTemplateAdd.send(message);

        return futureResponse;
    }

    public CompletableFuture<String> sendDelete(DeletePartDetailExternal partDetail) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<String> futureResponse = new CompletableFuture<>();
        pendingResponses.put(correlationId, futureResponse);

        Message<DeletePartDetailExternal> message = MessageBuilder
                .withPayload(partDetail)
                .setHeader(KafkaHeaders.TOPIC, "part-detail")
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .build();

        kafkaTemplateDelete.send(message);

        return futureResponse;
    }

    @KafkaListener(topics = "part-detail-response", groupId = "demoGroup")
    public void listenResponse(String response) {
        String[] parts = response.split("::");
        if (parts.length == 2) {
            String correlationId = parts[0];
            String message = parts[1];

            CompletableFuture<String> future = pendingResponses.remove(correlationId);
            if (future != null) {
                future.complete(message);
            } else {
                log.warn("No pending request for correlation-id: {}", correlationId);
                throw new IllegalStateException("No pending request for correlation-id: " + correlationId);
            }
        }
    }
}
