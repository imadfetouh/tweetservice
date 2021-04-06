package com.imadelfetouh.tweetservice.dal.configuration;

import com.imadelfetouh.tweetservice.dal.ormmodel.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class SessionWriteConfiguration {

    private final SessionFactory sessionFactory;

    public SessionWriteConfiguration() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://"+System.getenv("TWEETSERVICE_MYSQL_MASTER_HOST")+":"+System.getenv("TWEETSERVICE_MYSQL_MASTER_PORT")+"/tweetservice?createDatabaseIfNotExist=true");
        properties.put(Environment.USER, System.getenv("MYSQL_USER"));
        properties.put(Environment.PASS, System.getenv("MYSQL_PASS"));
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MariaDBDialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        properties.put(Environment.C3P0_MIN_SIZE, "1");
        properties.put(Environment.C3P0_MAX_SIZE, "2");
        properties.put(Environment.C3P0_ACQUIRE_INCREMENT, "1");
        properties.put(Environment.C3P0_TIMEOUT, "1800");
        configuration.setProperties(properties);

        configuration.addAnnotatedClass(Tweet.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Trend.class);
        configuration.addAnnotatedClass(TweetTrend.class);
        configuration.addAnnotatedClass(Following.class);

        sessionFactory = configuration.configure().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
