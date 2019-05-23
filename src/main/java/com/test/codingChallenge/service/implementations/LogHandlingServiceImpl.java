package com.test.codingChallenge.service.implementations;

import com.test.codingChallenge.dao.LogDao;
import com.test.codingChallenge.dto.ApplicationLog;
import com.test.codingChallenge.dto.State;
import com.test.codingChallenge.entities.ApplicationLogEntry;
import com.test.codingChallenge.service.LogHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogHandlingServiceImpl implements LogHandlingService {

    @Autowired
    private LogDao logDao;

    public void processLog(ApplicationLog applicationLog) {
        //temporary, can be removed when fileReader doesn't send whitespace at EOF.
        if (applicationLog != null) {
            ApplicationLogEntry initialLog = logDao.findByLogId(applicationLog.getId());
            if (initialLog != null && initialLog.getState().equals(State.STARTED)
                    && applicationLog.getState().equals(State.FINISHED)) {
                updateLog(initialLog, applicationLog.getTimeStamp(), isAlertRequired(initialLog.getStartTime(), applicationLog.getTimeStamp()));
            } else if (applicationLog.getState().equals(State.STARTED)) {
                createLogEntry(applicationLog);
            }
        }
    }

    public List<ApplicationLogEntry> showAlertLogs()
    {
        return logDao.findAllByAlertIsTrue();
    }

    private boolean isAlertRequired(long startTime, long endTime) {
        return (endTime - startTime) > 4;
    }

    private void updateLog(ApplicationLogEntry applicationLogEntry, long finishedTimestamp, boolean alertRequired) {
        applicationLogEntry.setEndTime(finishedTimestamp);
        applicationLogEntry.setAlert(alertRequired);
        applicationLogEntry.setState(State.FINISHED);
        logDao.save(applicationLogEntry);
    }

    private void createLogEntry(ApplicationLog applicationLog) {
        ApplicationLogEntry logEntry = new ApplicationLogEntry();
        logEntry.setLogId(applicationLog.getId());
        logEntry.setHost(applicationLog.getHost());
        logEntry.setState(applicationLog.getState());
        logEntry.setType(applicationLog.getType());
        logEntry.setStartTime(applicationLog.getTimeStamp());
        logEntry.setAlert(false);
        logDao.save(logEntry);
    }
}
