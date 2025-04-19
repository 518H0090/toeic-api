package com.toeic.user.infrastructure.stream;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.toeic.user.domain.exception.BadRequestException;
import com.toeic.user.domain.model.external.ExamExternal;
import com.toeic.user.domain.model.external.add.AddExamExternal;
import com.toeic.user.domain.model.external.relation.ExamIncludePartExternal;
import com.toeic.user.domain.model.external.update.UpdateExamExternal;
import com.toeic.user.infrastructure.producer.ExamProducer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExamStream {

    private final WebClient _webClient;
    private final ExamProducer _producer;

    public ExamStream(WebClient.Builder webClientBuilder, ExamProducer producer) {
        _webClient = webClientBuilder.baseUrl("http://localhost:8087/exams")
                .build();
        _producer = producer;
    }

    public Flux<ExamExternal> getExams() {
        return _webClient.get()
                .retrieve()
                .bodyToFlux(ExamExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public Mono<ExamExternal> getExamById(int examId) {
        return _webClient.get()
                .uri("/get/" + examId)
                .retrieve()
                .bodyToMono(ExamExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public Mono<ExamIncludePartExternal> getExamIncludePartById(int examId) {
        return _webClient.get()
                .uri("/get-include-part/" + examId)
                .retrieve()
                .bodyToMono(ExamIncludePartExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public String createExamPublishers(AddExamExternal request) {
        try {
            ExamExternal exam = new ExamExternal(
                request.getExam_name(), 
                request.getDescription(), 
                request.getCreated_by(), 
                request.getIs_public(),
                "CREATE"
            );

            var result = _producer.send(exam).get();

            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka create response");
    
            return result; 
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String updateExamPublishers(UpdateExamExternal request) {

        try {
            ExamExternal exam = new ExamExternal(
                request.getExam_id(), 
                request.getExam_name(), 
                request.getDescription(), 
                request.getIs_public(),
                "UPDATE"
            );
    
            var result = _producer.send(exam).get();
    
            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka update response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String deleteExamPublishers(Integer examId) {
        try {
            ExamExternal exam = new ExamExternal(
                examId, 
                "DELETE"
            );
    
            var result = _producer.send(exam).get();
    
            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka delete response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }
}
