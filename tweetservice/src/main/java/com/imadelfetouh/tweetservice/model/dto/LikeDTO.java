package com.imadelfetouh.tweetservice.model.dto;

import java.io.Serializable;

public class LikeDTO implements Serializable {

    private String userId;
    private String tweetId;

    public LikeDTO() {

    }

    public LikeDTO(String userId, String tweetId) {
        this.userId = userId;
        this.tweetId = tweetId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetId() {
        return tweetId;
    }
}
