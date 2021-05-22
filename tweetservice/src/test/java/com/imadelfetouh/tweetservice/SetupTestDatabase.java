package com.imadelfetouh.tweetservice;

import com.imadelfetouh.tweetservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.tweetservice.dal.ormmodel.User;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.hibernate.Session;

public class SetupTestDatabase implements QueryExecuter<Void> {
    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        User user = new User("u123", "imad", "imad.jpg");

        session.persist(user);

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
