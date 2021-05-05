package com.imadelfetouh.tweetservice.dal.db;

import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.queryexecuter.GetTweetMentionsExecuter;
import com.imadelfetouh.tweetservice.dalinterface.MentionDal;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MentionDalDB implements MentionDal {

    @Override
    public ResponseModel<List<TweetDTO>> getTweetMentions(String userId) {
        Executer<List<TweetDTO>> executer = new Executer<>(SessionType.READ);
        return executer.execute(new GetTweetMentionsExecuter(userId));
    }
}
