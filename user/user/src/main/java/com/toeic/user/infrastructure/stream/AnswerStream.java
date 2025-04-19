package com.toeic.user.infrastructure.stream;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.toeic.user.domain.exception.BadRequestException;
import com.toeic.user.domain.model.external.AnswerExternal;
import com.toeic.user.domain.model.external.add.AddAnswerExternal;
import com.toeic.user.domain.model.external.update.UpdateAnswerExternal;
import com.toeic.user.infrastructure.producer.AnswerProducer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnswerStream {
    private final WebClient _webClient;
    private final AnswerProducer _producer;

    public AnswerStream(WebClient.Builder webClientBuilder, AnswerProducer producer) {
        _webClient = webClientBuilder.baseUrl("http://localhost:8087/answers")
                .build();
        _producer = producer;
    }

    public Flux<AnswerExternal> getAnswers() {
        return _webClient.get()
                .retrieve()
                .bodyToFlux(AnswerExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public Mono<AnswerExternal> getAnswerById(int answerId) {
        return _webClient.get()
                .uri("/get/" + answerId)
                .retrieve()
                .bodyToMono(AnswerExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public String createAnswerPublishers(AddAnswerExternal request) {
        try {
            AnswerExternal answer = new AnswerExternal(
                    request.getQuestion_id(),
                    request.getText(),
                    request.getIs_correct(),
                    "CREATE");

            var result = _producer.send(answer).get();

            if (result.contains("Error processing event"))
                throw new BadRequestException("Error Processing for Kafka create response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String updateAnswerPublishers(UpdateAnswerExternal request) {

        try {
            AnswerExternal answer = new AnswerExternal(
                    request.getAnswer_id(),
                    request.getQuestion_id(),
                    request.getText(),
                    request.getIs_correct(),
                    request.getOrder_number(),
                    "UPDATE");

            var result = _producer.send(answer).get();

            if (result.contains("Error processing event"))
                throw new BadRequestException("Error Processing for Kafka update response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String deleteAnswerPublishers(int answerId) {
        try {
            AnswerExternal answer = new AnswerExternal(
                    answerId,
                    "DELETE");

            var result = _producer.send(answer).get();

            if (result.contains("Error processing event"))
                throw new BadRequestException("Error Processing for Kafka delete response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }
}
