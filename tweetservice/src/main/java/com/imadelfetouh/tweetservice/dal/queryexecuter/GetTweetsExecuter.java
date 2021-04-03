package com.imadelfetouh.tweetservice.dal.queryexecuter;

import com.imadelfetouh.tweetservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.tweetservice.dal.ormmodel.Tweet;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.dto.UserDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GetTweetsExecuter implements QueryExecuter<List<TweetDTO>> {

    @Override
    public ResponseModel<List<TweetDTO>> executeQuery(Session session) {
        ResponseModel<List<TweetDTO>> responseModel = new ResponseModel<>();

        Query query = session.createQuery("SELECT t FROM Tweet t JOIN FETCH t.user");
        List<Tweet> tweets = query.getResultList();
        List<TweetDTO> tweetDTOS = new ArrayList<>();

        for(Tweet tweet : tweets) {
            TweetDTO tweetDTO = new TweetDTO(tweet.getTweetId(), tweet.getContent(), tweet.getDate(), tweet.getTime(), tweet.getLikes(), new UserDTO(tweet.getUser().getUserId(), tweet.getUser().getUsername(), tweet.getUser().getUserphoto()));
            tweetDTOS.add(tweetDTO);
        }

        responseModel.setResponseType((tweetDTOS.isEmpty()) ? ResponseType.EMPTY : ResponseType.CORRECT);
        responseModel.setData(tweetDTOS);

        return responseModel;
    }
}
