package com.imadelfetouh.tweetservice.dal.db;

import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.queryexecuter.GetTrendsExecuter;
import com.imadelfetouh.tweetservice.dal.queryexecuter.GetTweetTrendExecuter;
import com.imadelfetouh.tweetservice.dalinterface.TrendDal;
import com.imadelfetouh.tweetservice.model.dto.TrendDTO;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrendDalDB implements TrendDal {

    @Override
    public ResponseModel<List<TrendDTO>> getTrends() {
        Executer<List<TrendDTO>> executer = new Executer<>(SessionType.READ);
        return executer.execute(new GetTrendsExecuter());
    }

    @Override
    public ResponseModel<List<TweetDTO>> getTweetTrends(String trend) {
        Executer<List<TweetDTO>> executer = new Executer<>(SessionType.READ);
        return executer.execute(new GetTweetTrendExecuter(trend));
    }
}
