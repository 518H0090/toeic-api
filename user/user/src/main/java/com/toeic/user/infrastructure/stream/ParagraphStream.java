package com.toeic.user.infrastructure.stream;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.toeic.user.domain.exception.BadRequestException;
import com.toeic.user.domain.model.external.ParagraphExternal;
import com.toeic.user.domain.model.external.add.AddParagraphExternal;
import com.toeic.user.domain.model.external.update.UpdateParagraphExternal;
import com.toeic.user.infrastructure.producer.ParagraphProducer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ParagraphStream {

     private final WebClient _webClient;
     private final ParagraphProducer _producer;

    public ParagraphStream(WebClient.Builder webClientBuilder, ParagraphProducer producer) {
        _webClient = webClientBuilder.baseUrl("http://localhost:8087/paragraphs")
                .build();
        _producer = producer;
    }

    public Flux<ParagraphExternal> getParagraphs() {
        return _webClient.get()
                .retrieve()
                .bodyToFlux(ParagraphExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public Mono<ParagraphExternal> getParagraphById(int paragraphId) {
        return _webClient.get()
                .uri("/get/" + paragraphId)
                .retrieve()
                .bodyToMono(ParagraphExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public String createParagraphPublishers(AddParagraphExternal request) {
        try {
            ParagraphExternal paragraph = new ParagraphExternal(
                request.getContent(),
                request.getCreated_by(),
                request.getIs_public(),
                "CREATE"
            );

            var result = _producer.send(paragraph).get();

            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka create response");
    
            return result; 
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String updateParagraphPublishers(UpdateParagraphExternal request) {

        try {
            ParagraphExternal paragraph = new ParagraphExternal(
                request.getParagraph_id(),
                request.getContent(),
                request.getIs_public(),
                "UPDATE"
            );
    
            var result = _producer.send(paragraph).get();
    
            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka update response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String deleteParagraphPublishers(Integer paragraphId) {
        try {

            ParagraphExternal paragraph = new ParagraphExternal(
                paragraphId, 
                "DELETE"
            );
    
            var result = _producer.send(paragraph).get();
    
            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka delete response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }
}
