package com.test.codingChallenge.service.implementations;

import com.test.codingChallenge.dao.LogDao;
import com.test.codingChallenge.dto.ApplicationLog;
import com.test.codingChallenge.dto.State;
import com.test.codingChallenge.entities.ApplicationLogEntry;
import com.test.codingChallenge.service.LogHandlingService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class LogHandlingServiceImplTest {

    @Tested
    LogHandlingService logHandlingService;

    @Injectable
    private LogDao logDao;

    private ApplicationLog startedLog;
    private ApplicationLog finishedLog;
    private ApplicationLogEntry responseStartedLogEntry;

    @Before
    public void setup() {
        logHandlingService = new LogHandlingServiceImpl();
        startedLog = new ApplicationLog("id", State.STARTED, "APPLICATION_LOG", "host", 1000);
        finishedLog = new ApplicationLog("id", State.FINISHED, "APPLICATION_LOG", "host", 1005);
        responseStartedLogEntry = new ApplicationLogEntry();
        responseStartedLogEntry.setState(State.STARTED);
        responseStartedLogEntry.setLogId(startedLog.getId());
        responseStartedLogEntry.setStartTime(startedLog.getTimeStamp());
        responseStartedLogEntry.setType(startedLog.getType());
        responseStartedLogEntry.setHost(startedLog.getHost());
    }

    @Test
    public void processLog_startedLogEntry() {

        List<ApplicationLogEntry> capture = new ArrayList<>();

        new Expectations() {
            {
                logDao.findByLogId(startedLog.getId());
                result = null;

                logDao.save(withCapture(capture));
            }
        };

        logHandlingService.processLog(startedLog);

        assertNotNull(capture);
        ApplicationLogEntry capturedEntry = capture.get(0);
        assertEquals(startedLog.getId(), capturedEntry.getLogId());
        assertEquals(startedLog.getHost(), capturedEntry.getHost());
        assertEquals(startedLog.getType(), capturedEntry.getType());
        assertEquals(startedLog.getState(), capturedEntry.getState());
        assertFalse(capturedEntry.isAlert());
    }

    @Test
    public void processLog_finishedLogEntry_longExecutionTime() {
        List<ApplicationLogEntry> capture = new ArrayList<>();

        finishedLogEntryExpectations(capture);

        logHandlingService.processLog(finishedLog);

        ApplicationLogEntry capturedEntry = capture.get(0);
        defaultFinishedLogEntryAssertions(capturedEntry);
        assertEquals(State.FINISHED, capturedEntry.getState());
        assertTrue(capturedEntry.isAlert());
    }

    @Test
    public void processLog_finishedLogEntry_shortExecutionTime() {
        List<ApplicationLogEntry> capture = new ArrayList<>();
        finishedLog.setTimeStamp(1001);

        finishedLogEntryExpectations(capture);

        logHandlingService.processLog(finishedLog);

        ApplicationLogEntry capturedEntry = capture.get(0);
        defaultFinishedLogEntryAssertions(capturedEntry);
        assertEquals(State.FINISHED, capturedEntry.getState());
        assertFalse(capturedEntry.isAlert());
    }

    @Test
    public void showAlertLogs() {

        new Expectations() {
            {
                logDao.findAllByAlertIsTrue();
                result = Collections.singletonList(responseStartedLogEntry);
            }
        };

        List<ApplicationLogEntry> result = logHandlingService.showAlertLogs();

        assertEquals(1, result.size());
        assertEquals(responseStartedLogEntry, result.get(0));
    }

    private void defaultFinishedLogEntryAssertions(ApplicationLogEntry capturedEntry) {
        assertNotNull(capturedEntry);
        assertEquals(startedLog.getId(), capturedEntry.getLogId());
        assertEquals(startedLog.getHost(), capturedEntry.getHost());
        assertEquals(startedLog.getType(), capturedEntry.getType());
    }

    private void finishedLogEntryExpectations(List<ApplicationLogEntry> capture) {

        new Expectations() {
            {
                logDao.findByLogId(startedLog.getId());
                result = responseStartedLogEntry;

                logDao.save(withCapture(capture));
            }
        };
    }
}