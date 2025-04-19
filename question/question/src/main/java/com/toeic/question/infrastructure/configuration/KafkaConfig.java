package com.toeic.question.infrastructure.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic examTopic() {
        return TopicBuilder.name("exam").build();
    }

    @Bean
    public NewTopic paragraphTopic() {
        return TopicBuilder.name("paragraph").build();
    }

    @Bean
    public NewTopic partTopic() {
        return TopicBuilder.name("part").build();
    }

    @Bean
    public NewTopic questionTopic() {
        return TopicBuilder.name("question").build();
    }

    @Bean
    public NewTopic answerTopic() {
        return TopicBuilder.name("answer").build();
    }

    @Bean
    public NewTopic partDetailTopic() {
        return TopicBuilder.name("part-detail").build();
    }

    @Bean
    public NewTopic examResponseTopic() {
        return TopicBuilder.name("exam-response").build();
    }

    @Bean
    public NewTopic paragraphResponseTopic() {
        return TopicBuilder.name("paragraph-response").build();
    }

    @Bean
    public NewTopic partResponseTopic() {
        return TopicBuilder.name("part-response").build();
    }

    @Bean
    public NewTopic questionResponseTopic() {
        return TopicBuilder.name("question-response").build();
    }

    @Bean
    public NewTopic answerResponseTopic() {
        return TopicBuilder.name("answer-response").build();
    }

    @Bean
    public NewTopic partDetailResponseTopic() {
        return TopicBuilder.name("part-detail-response").build();
    }
}
