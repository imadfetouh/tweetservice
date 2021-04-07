package com.imadelfetouh.tweetservice.dal.db;

import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.queryexecuter.AddTweetExecuter;
import com.imadelfetouh.tweetservice.dal.queryexecuter.GetTweetsExecuter;
import com.imadelfetouh.tweetservice.dalinterface.TweetDal;
import com.imadelfetouh.tweetservice.model.dto.NewTweetDTO;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TweetDalDB implements TweetDal {

    @Override
    public ResponseModel<List<TweetDTO>> getTweets() {
        Executer<List<TweetDTO>> executer = new Executer<>(SessionType.READ);
        return executer.execute(new GetTweetsExecuter());
    }

    @Override
    public ResponseModel<Void> addTweet(NewTweetDTO newTweetDTO) {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        return executer.execute(new AddTweetExecuter(newTweetDTO));
    }
}
