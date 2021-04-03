package com.imadelfetouh.tweetservice.dal.db;

import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.queryexecuter.LikeTweetExecutor;
import com.imadelfetouh.tweetservice.dalinterface.LikeDal;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import org.springframework.stereotype.Repository;

@Repository
public class LikeDalDB implements LikeDal {

    Executer<Void> executer;

    public LikeDalDB() {
        executer = new Executer<>(SessionType.WRITE);
    }

    @Override
    public ResponseModel<Void> likeTweet(String tweetId) {
        return executer.execute(new LikeTweetExecutor(tweetId));
    }
}
