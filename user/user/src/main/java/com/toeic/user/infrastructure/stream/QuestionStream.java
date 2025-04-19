package com.toeic.user.infrastructure.stream;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.toeic.user.domain.exception.BadRequestException;
import com.toeic.user.domain.model.external.QuestionExternal;
import com.toeic.user.domain.model.external.add.AddQuestionExternal;
import com.toeic.user.domain.model.external.update.UpdateQuestionExternal;
import com.toeic.user.infrastructure.producer.QuestionProducer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class QuestionStream {

    private final WebClient _webClient;
    private final QuestionProducer _producer;

    public QuestionStream(WebClient.Builder webClientBuilder, QuestionProducer producer) {
        _webClient = webClientBuilder.baseUrl("http://localhost:8087/questions")
                .build();
        _producer = producer;
    }

    public Flux<QuestionExternal> getQuestions() {
        return _webClient.get()
                .retrieve()
                .bodyToFlux(QuestionExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public Mono<QuestionExternal> getQuestionById(int questionId) {
        return _webClient.get()
                .uri("/get/" + questionId)
                .retrieve()
                .bodyToMono(QuestionExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public String createQuestionPublishers(AddQuestionExternal request) {
        try {
            QuestionExternal question = new QuestionExternal(
                    request.getDescription(),
                    request.getIs_multiple_choice(),
                    request.getContentType(),
                    request.getContent(),
                    request.getParagraph_id(),
                    request.getCreated_by(),
                    request.getIs_public(),
                    "CREATE");

            var result = _producer.send(question).get();

            if (result.contains("Error processing event"))
                throw new BadRequestException("Error Processing for Kafka create response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String updateQuestionPublishers(UpdateQuestionExternal request) {

        try {
            QuestionExternal question = new QuestionExternal(
                    request.getQuestion_id(),
                    request.getDescription(),
                    request.getIs_multiple_choice(),
                    request.getContentType(),
                    request.getContent(),
                    request.getParagraph_id(),
                    request.getIs_public(),
                    "UPDATE");

            var result = _producer.send(question).get();

            if (result.contains("Error processing event"))
                throw new BadRequestException("Error Processing for Kafka update response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String deleteQuestionPublishers(int questionId) {
        try {
            QuestionExternal question = new QuestionExternal(
                    questionId,
                    "DELETE");

            var result = _producer.send(question).get();

            if (result.contains("Error processing event"))
                throw new BadRequestException("Error Processing for Kafka delete response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }
}
