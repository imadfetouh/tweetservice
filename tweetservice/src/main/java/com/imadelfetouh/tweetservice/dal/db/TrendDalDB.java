package com.imadelfetouh.tweetservice.dal.db;

import com.imadelfetouh.tweetservice.dalinterface.TrendDal;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;

public class TrendDalDB implements TrendDal {
    @Override
    public ResponseModel<TweetDTO> getTrends(String trend) {
        return null;
    }
}
