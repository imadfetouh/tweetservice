package com.imadelfetouh.tweetservice.dalinterface;

import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;

import java.util.List;

public interface MentionDal {

    ResponseModel<List<TweetDTO>> getTweetMentions(String userId);
}
