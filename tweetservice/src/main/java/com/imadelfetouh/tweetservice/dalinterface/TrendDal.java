package com.imadelfetouh.tweetservice.dalinterface;

import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;

public interface TrendDal {

    ResponseModel<TweetDTO> getTrends(String trend);
}
