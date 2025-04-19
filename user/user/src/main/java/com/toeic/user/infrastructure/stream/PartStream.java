package com.toeic.user.infrastructure.stream;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.toeic.user.domain.exception.BadRequestException;
import com.toeic.user.domain.model.external.PartExternal;
import com.toeic.user.domain.model.external.add.AddPartExternal;
import com.toeic.user.domain.model.external.update.UpdatePartExternal;
import com.toeic.user.infrastructure.producer.PartProducer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PartStream {
    
    private final WebClient _webClient;
    private final PartProducer _producer;

    public PartStream(WebClient.Builder webClientBuilder, PartProducer producer) {
        _webClient = webClientBuilder.baseUrl("http://localhost:8087/parts")
                .build();
        _producer = producer;
    }

    public Flux<PartExternal> getParts() {
        return _webClient.get()
                .retrieve()
                .bodyToFlux(PartExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public Mono<PartExternal> getPartById(int partId) {
        return _webClient.get()
                .uri("/get/" + partId)
                .retrieve()
                .bodyToMono(PartExternal.class)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

     public String createPartPublishers(AddPartExternal request) {
        try {
            PartExternal part = new PartExternal(
                request.getPart_name(),
                request.getCreated_by(),
                request.getIs_public(),
                request.getExam_id(),
                "CREATE"
            );

            var result = _producer.send(part).get();

            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka create response");
    
            return result; 
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String updatePartPublishers(UpdatePartExternal request) {

        try {
            PartExternal part = new PartExternal(
                request.getPart_id(), 
                request.getPart_name(), 
                request.getIs_public(), 
                request.getExam_id(),
                "UPDATE"
            );
    
            var result = _producer.send(part).get();
    
            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka update response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }

    public String deletePartPublishers(Integer partId) {
        try {
            PartExternal part = new PartExternal(
                partId, 
                "DELETE"
            );
    
            var result = _producer.send(part).get();
    
            if (result.contains("Error processing event")) throw new BadRequestException("Error Processing for Kafka delete response");

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Error waiting for Kafka response: " + e.getMessage());
        }
    }
}
