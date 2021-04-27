package com.imadelfetouh.tweetservice.model.dto;

public class TweetDTO {

    private String tweetId;
    private String content;
    private Long date;
    private String time;
    private int likes;
    private UserDTO user;

    public TweetDTO(String tweetId, String content, Long date, String time, int likes, UserDTO user) {
        this.tweetId = tweetId;
        this.content = content;
        this.date = date;
        this.time = time;
        this.likes = likes;
        this.user = user;
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

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
