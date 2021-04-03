package com.imadelfetouh.tweetservice.dal.ormmodel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "trend")
public class Trend implements Serializable {

    public Trend() {

    }

    public Trend(String trendId, String name) {
        this.trendId = trendId;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "trendId")
    private String trendId;

    @Column(name = "name")
    private String name;
}
