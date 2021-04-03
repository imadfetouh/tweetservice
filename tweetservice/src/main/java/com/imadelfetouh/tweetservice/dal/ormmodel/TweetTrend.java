package com.imadelfetouh.tweetservice.dal.ormmodel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tweettrend")
public class TweetTrend implements Serializable {

    public TweetTrend() {

    }

    public TweetTrend(Tweet tweet, Trend trend) {
        this.tweet = tweet;
        this.trend = trend;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id", referencedColumnName = "tweetId")
    private Tweet tweet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trend_id", referencedColumnName = "trendId")
    private Trend trend;
}
