package com.imadelfetouh.tweetservice.dalinterface;

import com.imadelfetouh.tweetservice.model.dto.NewTweetDTO;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;

import java.util.List;

public interface TweetDal {

    ResponseModel<List<TweetDTO>> getTweets();

    ResponseModel<Void> addTweet(NewTweetDTO newTweetDTO);
}
