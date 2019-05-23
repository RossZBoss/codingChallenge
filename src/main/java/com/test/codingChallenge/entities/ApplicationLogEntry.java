package com.test.codingChallenge.entities;

import com.test.codingChallenge.dto.State;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Log")
public class ApplicationLogEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "logId")
    private String logId;

    @Column(name = "state")
    private State state;

    @Column(name = "type")
    private String type;

    @Column(name = "host")
    private String host;

    @Column(name = "starttime")
    private long startTime;

    @Column(name = "endtime")
    private long endTime;

    @Column(name = "alert")
    private boolean alert;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
