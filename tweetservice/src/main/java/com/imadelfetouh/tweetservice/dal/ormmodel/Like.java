package com.imadelfetouh.tweetservice.dal.ormmodel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tweetlike")
public class Like implements Serializable {

    public Like() {

    }

    public Like(Tweet tweet, User user) {
        this.tweet = tweet;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_Id", referencedColumnName = "tweetId")
    private Tweet tweet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", referencedColumnName = "userId")
    private User user;

    public User getUser() {
        return user;
    }
}
