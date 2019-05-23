package com.test.codingChallenge.dto;

public class ApplicationLog extends Log {

    private String host;
    private String type;

    public ApplicationLog()
    {
    }

    public ApplicationLog(String id, State state, String type, String host, long timeStamp) {
        super(id, state, timeStamp);
        this.host = host;
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
