package com.test.codingChallenge.endpoint;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "readFileInputChannel")
public interface FileHandlingGateway {
    void putFileDirectoryOnChannel(String filePath);
}
