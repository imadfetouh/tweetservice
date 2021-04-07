package com.imadelfetouh.tweetservice.dal.ormmodel;

import javax.persistence.*;

@Entity
@Table(name = "tweetmention")
public class TweetMention {

    public TweetMention() {

    }

    public TweetMention(Tweet tweet, User userMention) {
        this.tweet = tweet;
        this.userMention = userMention;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id", referencedColumnName = "tweetId")
    private Tweet tweet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User userMention;
}
