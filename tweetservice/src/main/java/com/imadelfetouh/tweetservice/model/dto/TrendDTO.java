package com.imadelfetouh.tweetservice.model.dto;

import java.io.Serializable;

public class TrendDTO implements Serializable {

    private String trend;

    public TrendDTO(String trend) {
        this.trend = trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getTrend() {
        return trend;
    }
}
