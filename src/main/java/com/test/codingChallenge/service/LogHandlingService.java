package com.test.codingChallenge.service;

import com.test.codingChallenge.dto.ApplicationLog;
import com.test.codingChallenge.entities.ApplicationLogEntry;

import java.util.List;

public interface LogHandlingService {

    void processLog(ApplicationLog applicationLog);

    List<ApplicationLogEntry> showAlertLogs();
}
