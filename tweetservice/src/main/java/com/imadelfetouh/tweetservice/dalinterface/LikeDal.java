package com.imadelfetouh.tweetservice.dalinterface;

import com.imadelfetouh.tweetservice.model.response.ResponseModel;

public interface LikeDal {

    ResponseModel<Void> likeTweet(String tweetId);
}
