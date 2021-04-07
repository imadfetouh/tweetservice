package com.imadelfetouh.tweetservice.dal.configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class SessionReadConfiguration {

    private final SessionFactory sessionFactory;

    public SessionReadConfiguration() {
        Configuration configuration = new Configuration();
        configuration.getProperties().put(Environment.URL, "jdbc:mysql://"+System.getenv("TWEETSERVICE_MYSQL_REPLICA_HOST")+":"+System.getenv("TWEETSERVICE_MYSQL_REPLICA_PORT")+"/tweetservice");

        sessionFactory = configuration.configure().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
