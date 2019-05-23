package com.test.codingChallenge.endpoint;

import com.test.codingChallenge.entities.ApplicationLogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class UserInputReader {


    private FileHandlingGateway fileHandlingGateway;
    private AlertRequestGateway alertRequestGateway;

    @Autowired
    public UserInputReader(FileHandlingGateway fileHandlingGateway, AlertRequestGateway alertRequestGateway) {
        this.fileHandlingGateway = fileHandlingGateway;
        this.alertRequestGateway = alertRequestGateway;
    }


    @ShellMethod(key = "readFile", value = "to use this command type: readFile <absolute-path-to-file> eg: readFile C:/test.txt")
    public String readFile(String filePath) {
        fileHandlingGateway.putFileDirectoryOnChannel(filePath);
        return "file read complete";
    }

    @ShellMethod(key = "getAlertLogs", value = "to use this command type: getAlertLogs")
    public String getAlertLogs() {
        return alertRequestGateway.getAlertLogs();
    }
}
