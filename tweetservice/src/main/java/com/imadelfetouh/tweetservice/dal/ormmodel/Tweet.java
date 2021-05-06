package com.imadelfetouh.tweetservice.dal.ormmodel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tweet")
public class Tweet implements Serializable {

    public Tweet() {

    }

    public Tweet(String tweetId, String content, Long date, String time, User user) {
        this.tweetId = tweetId;
        this.content = content;
        this.date = date;
        this.time = time;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tweet_id", referencedColumnName = "tweetId")
    private List<Like> likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", referencedColumnName = "userId")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tweet_id", referencedColumnName = "tweetId")
    private List<TweetTrend> tweetTrends;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tweet_id", referencedColumnName = "tweetId")
    private List<TweetMention> tweetMentions;

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

    public User getUser() {
        return user;
    }

    public List<Like> getLikes() {
        return likes;
    }
}
