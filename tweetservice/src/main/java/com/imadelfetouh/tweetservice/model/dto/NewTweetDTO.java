package com.imadelfetouh.tweetservice.model.dto;

public class NewTweetDTO {

    private String userId;
    private String content;

    public NewTweetDTO(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
