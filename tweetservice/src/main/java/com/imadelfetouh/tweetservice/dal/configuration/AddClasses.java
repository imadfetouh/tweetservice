package com.imadelfetouh.tweetservice.dal.configuration;

import com.imadelfetouh.tweetservice.dal.ormmodel.*;
import org.hibernate.cfg.Configuration;

public class AddClasses {

    private static final AddClasses addClasses = new AddClasses();

    private AddClasses() {

    }

    public static AddClasses getInstance() {
        return addClasses;
    }

    public void setClasses(Configuration configuration) {
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Following.class);
        configuration.addAnnotatedClass(Tweet.class);
        configuration.addAnnotatedClass(TweetMention.class);
        configuration.addAnnotatedClass(TweetTrend.class);
        configuration.addAnnotatedClass(Trend.class);
        configuration.addAnnotatedClass(Like.class);
    }
}
