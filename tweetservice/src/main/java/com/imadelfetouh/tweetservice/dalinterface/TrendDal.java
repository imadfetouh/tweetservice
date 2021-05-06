package com.imadelfetouh.tweetservice.dalinterface;

import com.imadelfetouh.tweetservice.model.dto.TrendDTO;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;

import java.util.List;

public interface TrendDal {

    ResponseModel<List<TrendDTO>> getTrends();

    ResponseModel<List<TweetDTO>> getTweetTrends(String userId, String trend);
}
