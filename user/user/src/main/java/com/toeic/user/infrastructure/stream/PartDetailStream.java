package com.toeic.user.infrastructure.stream;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.toeic.user.domain.exception.BadRequestException;
import com.toeic.user.domain.model.external.PartDetailExternal;
import com.toeic.user.domain.model.external.relation.AddPartDetailExternal;
import com.toeic.user.domain.model.external.relation.DeletePartDetailExternal;
import com.toeic.user.infrastructure.producer.PartDetailProducer;

import reactor.core.publisher.Flux;

@Service
public class PartDetailStream {

    private final WebClient _webClient;
    private final PartDetailProducer _producer;

    public PartDetailStream(WebClient.Builder webClientBuilder, PartDetailProducer producer) {
        _webClient = webClientBuilder.baseUrl("http://localhost:8087/part-details")
                .build();
        _producer = producer;
    }

    public Flux<PartDetailExternal> getPartDetails() {
        return _webClient.get()
                .retrieve()
                .bodyToFlux(PartDetailExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public Flux<PartDetailExternal> getPartDetailById(int partId) {
        return _webClient.get()
                .uri("/get/" + partId)
                .retrieve()
                .bodyToFlux(PartDetailExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

     public String createPartDetailPublishers(AddPartDetailExternal request) {
        try {
            AddPartDetailExternal partDetail = new AddPartDetailExternal(
               request.getPart_id(),
               request.getQuestion_id(),
               request.getParagraph_id(),
               request.getOrder_number()
            );

            var result = _producer.sendAdd(partDetail).get();

            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka create response");
    
            return result; 
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String deletePartDetailPublishers(DeletePartDetailExternal request) {
        try {
            DeletePartDetailExternal part = new DeletePartDetailExternal(
                request.getPart_id(),
                request.getQuestion_id(),
                request.getParagraph_id()
            );
    
            var result = _producer.sendDelete(part).get();
    
            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka delete response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }
}
