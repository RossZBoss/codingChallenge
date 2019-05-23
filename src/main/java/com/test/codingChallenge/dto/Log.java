package com.test.codingChallenge.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Log {

    private String id;
    private State state;
    @JsonProperty("timestamp")
    private long timeStamp;

    public Log() {
    }

    public Log(String id, State state, long timeStamp) {
        this.id = id;
        this.state = state;
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
