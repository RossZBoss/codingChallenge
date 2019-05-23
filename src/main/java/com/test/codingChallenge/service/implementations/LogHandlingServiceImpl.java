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
            if (initialLog != null) {
                updateLog(initialLog, applicationLog.getTimeStamp());
            } else {
                createLogEntry(applicationLog);
            }
        }
    }

    public List<ApplicationLogEntry> showAlertLogs() {
        return logDao.findAllByAlertIsTrue();
    }

    private boolean isAlertRequired(long startTime, long endTime) {
        return (endTime - startTime) > 4;
    }

    private void updateLog(ApplicationLogEntry initialApplicationLog, long startOrEndTimestamp) {
        boolean alertRequired;
        if (initialApplicationLog.getState().equals(State.STARTED)) {
            alertRequired = isAlertRequired(initialApplicationLog.getStartTime(), startOrEndTimestamp);
            initialApplicationLog.setEndTime(startOrEndTimestamp);
        } else {
            alertRequired = isAlertRequired(startOrEndTimestamp, initialApplicationLog.getEndTime());
            initialApplicationLog.setStartTime(startOrEndTimestamp);
        }
        initialApplicationLog.setAlert(alertRequired);
        initialApplicationLog.setState(State.FINISHED);
        logDao.save(initialApplicationLog);
    }

    private void createLogEntry(ApplicationLog applicationLog) {
        ApplicationLogEntry logEntry = new ApplicationLogEntry();
        logEntry.setLogId(applicationLog.getId());
        logEntry.setHost(applicationLog.getHost());
        logEntry.setState(applicationLog.getState());
        logEntry.setType(applicationLog.getType());
        if (applicationLog.getState().equals(State.STARTED))
            logEntry.setStartTime(applicationLog.getTimeStamp());
        else
            logEntry.setEndTime(applicationLog.getTimeStamp());
        logEntry.setAlert(false);
        logDao.save(logEntry);
    }
}
