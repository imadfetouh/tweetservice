package com.imadelfetouh.tweetservice.dal.configuration;

import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import org.hibernate.Session;

public interface QueryExecuter<T> {

    ResponseModel<T> executeQuery(Session session);
}
