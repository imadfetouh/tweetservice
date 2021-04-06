package com.imadelfetouh.tweetservice.dal.ormmodel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tweet")
public class Tweet implements Serializable {

    public Tweet() {

    }

    public Tweet(String tweetId, String content, Long date, String time, Integer likes, User user) {
        this.tweetId = tweetId;
        this.content = content;
        this.date = date;
        this.time = time;
        this.likes = likes;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tweetId")
    private String tweetId;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Long date;

    @Column(name = "time")
    private String time;

    @Column(name = "likes")
    private Integer likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", referencedColumnName = "userId")
    private User user;

    public Integer getId() {
        return id;
    }

    public String getTweetId() {
        return tweetId;
    }

    public String getContent() {
        return content;
    }

    public Long getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Integer getLikes() {
        return likes;
    }

    public User getUser() {
        return user;
    }
}
