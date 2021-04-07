package com.imadelfetouh.tweetservice.dal.ormmodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tweet")
public class Tweet implements Serializable {

    public Tweet() {

    }

    public Tweet(String content, Long date, String time, Integer likes, User user) {
        this.content = content;
        this.date = date;
        this.time = time;
        this.likes = likes;
        this.user = user;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
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
