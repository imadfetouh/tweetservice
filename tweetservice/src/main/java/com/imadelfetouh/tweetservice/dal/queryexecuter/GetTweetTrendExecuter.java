package com.imadelfetouh.tweetservice.dal.queryexecuter;

import com.imadelfetouh.tweetservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.tweetservice.dal.ormmodel.Like;
import com.imadelfetouh.tweetservice.dal.ormmodel.Tweet;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.dto.UserDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GetTweetTrendExecuter implements QueryExecuter<List<TweetDTO>> {

    private String userId;
    private String trend;

    public GetTweetTrendExecuter(String userId, String trend) {
        this.userId = userId;
        this.trend = trend;
    }

    @Override
    public ResponseModel<List<TweetDTO>> executeQuery(Session session) {
        ResponseModel<List<TweetDTO>> responseModel = new ResponseModel<>();

        Query query = session.createQuery("SELECT tt.tweet FROM TweetTrend tt JOIN tt.tweet.user u JOIN FETCH tt.tweet.likes WHERE tt.trend.name = :trend");
        query.setParameter("trend", trend);

        List<Tweet> tweets = query.getResultList();
        List<TweetDTO> tweetDTOS = new ArrayList<>();

        for(Tweet tweet : tweets) {
            TweetDTO tweetDTO = new TweetDTO(tweet.getTweetId(), tweet.getContent(), tweet.getDate(), tweet.getTime(), tweet.getLikes().size(), new UserDTO(tweet.getUser().getUserId(), tweet.getUser().getUsername(), tweet.getUser().getPhoto()));
            Like like = tweet.getLikes().stream().filter(l -> l.getUser().getUserId().equals(userId)).findFirst().orElse(null);
            tweetDTO.setUserLiked((like != null));
            tweetDTOS.add(tweetDTO);
        }

        responseModel.setResponseType((tweetDTOS.isEmpty()) ? ResponseType.EMPTY : ResponseType.CORRECT);
        responseModel.setData(tweetDTOS);

        return responseModel;
    }
}
