package com.imadelfetouh.tweetservice.dal.queryexecuter;

import com.imadelfetouh.tweetservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;

public class LikeTweetExecuter implements QueryExecuter<Void> {

    private String tweetId;

    public LikeTweetExecuter(String tweetId) {
        this.tweetId = tweetId;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        Query query = session.createQuery("UPDATE Tweet t SET t.likes = t.likes + :incrementValue");
        query.setParameter("incrementValue", 1);

        query.executeUpdate();

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);
        return responseModel;
    }
}
