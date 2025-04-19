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

import com.toeic.user.domain.model.external.QuestionExternal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionProducer {
    private final KafkaTemplate<String, QuestionExternal> kafkaTemplate;
    private final ConcurrentHashMap<String, CompletableFuture<String>> pendingResponses = new ConcurrentHashMap<>();

    public CompletableFuture<String> send(QuestionExternal exam) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<String> futureResponse = new CompletableFuture<>();
        pendingResponses.put(correlationId, futureResponse);

        Message<QuestionExternal> message = MessageBuilder
                .withPayload(exam)
                .setHeader(KafkaHeaders.TOPIC, "question")
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .build();

        kafkaTemplate.send(message);

        return futureResponse;
    }

    @KafkaListener(topics = "question-response", groupId = "demoGroup")
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
