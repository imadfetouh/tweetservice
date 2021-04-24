package com.imadelfetouh.tweetservice.model.dto;

public class NewTweetDTO {

    private String tweetId;
    private String content;
    private Long date;
    private String time;
    private Integer likes;
    private String userId;

    public NewTweetDTO(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
