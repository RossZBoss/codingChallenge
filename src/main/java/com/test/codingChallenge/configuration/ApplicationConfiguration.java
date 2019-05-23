package com.test.codingChallenge.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enableDefaultTyping();
        return objectMapper;
    }

    @Bean
    public MessageChannel readFileInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel stringToJsonChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel logProcessingChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel alertChannel() {
        return new DirectChannel();
    }
}
