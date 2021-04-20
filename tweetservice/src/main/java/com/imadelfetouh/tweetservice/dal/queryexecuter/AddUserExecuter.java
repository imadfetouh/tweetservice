package com.imadelfetouh.tweetservice.dal.queryexecuter;

import com.imadelfetouh.tweetservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.tweetservice.dal.ormmodel.User;
import com.imadelfetouh.tweetservice.model.dto.NewUserDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.hibernate.Session;

public class AddUserExecuter implements QueryExecuter<Void> {

    private NewUserDTO newUserDTO;

    public AddUserExecuter(NewUserDTO newUserDTO) {
        this.newUserDTO = newUserDTO;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        User user = new User(newUserDTO.getUserId(), newUserDTO.getUsername(), newUserDTO.getPhoto());

        session.persist(user);

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
