package com.imadelfetouh.tweetservice.dal.queryexecuter;

import com.imadelfetouh.tweetservice.dal.ormmodel.Trend;
import com.imadelfetouh.tweetservice.dal.ormmodel.Tweet;
import com.imadelfetouh.tweetservice.dal.ormmodel.TweetTrend;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AddTrendExecutor {

    private static final AddTrendExecutor addTrendExecutor = new AddTrendExecutor();

    private AddTrendExecutor() {

    }

    public static AddTrendExecutor getInstance() {
        return addTrendExecutor;
    }

    public void addTrends(Tweet tweet, Session session) {
        List<String> trends = getTrendsFromTweet(tweet.getContent());
        for(String trend : trends) {
            try {
                Query query = session.createQuery("SELECT t FROM Trend t WHERE t.name = :trend");
                query.setParameter("trend", trend);
                Trend t = (Trend) query.getSingleResult();

                add(tweet, t, session);

            }
            catch (NoResultException e) {
                String trendId = UUID.randomUUID().toString();
                Trend newTrend = new Trend(trendId, trend);
                session.persist(newTrend);

                add(tweet, newTrend, session);
            }
        }
    }

    private List<String> getTrendsFromTweet(String content) {
        String parts[] = content.split(" ");

        return Arrays.stream(parts).filter(p -> p.startsWith("#", 0)).collect(Collectors.toList());
    }

    private void add(Tweet tweet, Trend trend, Session session) {
        TweetTrend tweetTrend = new TweetTrend(tweet, trend);
        session.persist(tweetTrend);
    }
}
