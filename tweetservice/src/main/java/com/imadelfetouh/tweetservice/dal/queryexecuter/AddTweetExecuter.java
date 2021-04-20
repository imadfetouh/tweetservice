package com.imadelfetouh.tweetservice.dal.queryexecuter;

import com.imadelfetouh.tweetservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.tweetservice.dal.ormmodel.Tweet;
import com.imadelfetouh.tweetservice.dal.ormmodel.User;
import com.imadelfetouh.tweetservice.model.datetime.DateTime;
import com.imadelfetouh.tweetservice.model.dto.NewTweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.UUID;

public class AddTweetExecuter implements QueryExecuter<Void> {

    private NewTweetDTO newTweetDTO;

    public AddTweetExecuter(NewTweetDTO newTweetDTO) {
        this.newTweetDTO = newTweetDTO;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        Long currentDate = DateTime.getInstance().getCurrentDate();
        String currentTime = DateTime.getInstance().getCurrentTime();

        User user = getUser(session);

        String tweetId = UUID.randomUUID().toString();

        Tweet tweet = new Tweet(tweetId, newTweetDTO.getContent(), currentDate, currentTime, 0, user);

        session.persist(tweet);

        AddTrend.getInstance().addTrends(tweet, session);
        AddMention.getInstance().addMentions(tweet, newTweetDTO.getUserId(), session);

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);
        return responseModel;
    }

    private User getUser(Session session) {
        Query query = session.createQuery("SELECT u FROM User u WHERE u.userId = :userId");
        query.setParameter("userId", newTweetDTO.getUserId());
        return (User) query.getSingleResult();
    }
}
