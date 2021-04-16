package com.imadelfetouh.tweetservice.dal.ormmodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "trend")
public class Trend implements Serializable {

    public Trend() {

    }

    public Trend(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "trendId")
    private String trendId;

    @Column(name = "name")
    private String name;
}
