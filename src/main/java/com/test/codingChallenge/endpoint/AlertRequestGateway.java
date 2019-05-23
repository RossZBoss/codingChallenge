package com.test.codingChallenge.endpoint;

import org.springframework.integration.annotation.MessagingGateway;


@MessagingGateway(defaultRequestChannel = "alertChannel", defaultPayloadExpression = "''")
public interface AlertRequestGateway {
    String getAlertLogs();
}
