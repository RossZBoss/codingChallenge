package com.test.codingChallenge.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.codingChallenge.configuration.ApplicationConfiguration;
import com.test.codingChallenge.dto.ApplicationLog;
import com.test.codingChallenge.entities.ApplicationLogEntry;
import com.test.codingChallenge.service.LogHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.*;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class ReadFileHandler {

    private ApplicationConfiguration applicationConfiguration;

    private ObjectMapper objectMapper;

    private LogHandlingService logHandlingService;

    @Autowired
    public ReadFileHandler(ApplicationConfiguration applicationConfiguration, ObjectMapper objectMapper, LogHandlingService logHandlingService) {
        this.applicationConfiguration = applicationConfiguration;
        this.objectMapper = objectMapper;
        this.logHandlingService = logHandlingService;
    }


    @ServiceActivator(inputChannel = "readFileInputChannel")
    public void fileHandler(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int character;
        while ((character = reader.read()) != -1) {
            if (((char) character) == '}') {
                content.append((char) character);
                applicationConfiguration.stringToJsonChannel().send(new GenericMessage<>(content.toString()));
                content = new StringBuilder();
            } else
                content.append((char) character);
        }
    }

    @ServiceActivator(inputChannel = "stringToJsonChannel", outputChannel = "logProcessingChannel")
    public ApplicationLog convertStringToJson(String log) throws IOException {
        return objectMapper.readValue(log, ApplicationLog.class);
    }

    @ServiceActivator(inputChannel = "logProcessingChannel")
    public void processLog(ApplicationLog log) {
        logHandlingService.processLog(log);
    }

    @ServiceActivator(inputChannel = "alertChannel")
    public String getAlertLogs() throws JsonProcessingException {
        return objectMapper.writeValueAsString(logHandlingService.showAlertLogs());
    }
}
