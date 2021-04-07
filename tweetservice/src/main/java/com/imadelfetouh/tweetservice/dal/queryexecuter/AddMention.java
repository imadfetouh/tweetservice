package com.imadelfetouh.tweetservice.dal.queryexecuter;

import com.imadelfetouh.tweetservice.dal.ormmodel.Tweet;
import com.imadelfetouh.tweetservice.dal.ormmodel.TweetMention;
import com.imadelfetouh.tweetservice.dal.ormmodel.User;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AddMention {

    private Logger logger = Logger.getLogger(AddMention.class.getName());

    private final static AddMention addMention = new AddMention();

    private AddMention() {

    }

    public static AddMention getInstance() {
        return addMention;
    }

    public void addMentions(Tweet tweet, String userId, Session session) {
        List<String> mentions = getMentionsFromTweet(tweet.getContent());
        for(String mention : mentions) {
            try {
                String username = mention.substring(1);
                Query query = session.createQuery("SELECT u FROM User u WHERE u.username = :mention AND u.userId IN (SELECT f.userFollowing.userId FROM Following f WHERE f.user.userId = :userId)");
                query.setParameter("mention", username);
                query.setParameter("userId", userId);
                User user = (User) query.getSingleResult();

                add(tweet, user, session);
            }
            catch (NoResultException e) {
                //user bestaat niet
                logger.log(Level.ALL, e.getMessage());
            }
        }
    }

    private List<String> getMentionsFromTweet(String content) {
        String[] parts = content.split(" ");

        return Arrays.stream(parts).filter(p -> p.startsWith("@", 0)).collect(Collectors.toList());
    }

    private void add(Tweet tweet, User user, Session session) {
        TweetMention tweetMention = new TweetMention(tweet, user);
        session.persist(tweetMention);
    }

}
