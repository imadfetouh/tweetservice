package com.imadelfetouh.tweetservice.dal.configuration;

public class ReadWriteConfiguration {

    private static final ReadWriteConfiguration readWriteConfiguration = new ReadWriteConfiguration();
    private SessionWriteConfiguration sessionWriteConfiguration;
    private SessionReadConfiguration sessionReadConfiguration;

    private ReadWriteConfiguration() {

    }

    public static ReadWriteConfiguration getInstance() {
        return readWriteConfiguration;
    }

    public SessionWriteConfiguration getSessionWriteConfiguration() {
        if(sessionWriteConfiguration == null) {
            sessionWriteConfiguration = new SessionWriteConfiguration();
            return sessionWriteConfiguration;
        }

        return sessionWriteConfiguration;
    }

    public SessionReadConfiguration getSessionReadConfiguration() {
        if(sessionReadConfiguration == null) {
            sessionReadConfiguration = new SessionReadConfiguration();
            return sessionReadConfiguration;
        }

        return sessionReadConfiguration;
    }
}
