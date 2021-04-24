package com.imadelfetouh.tweetservice.dal.queryexecuter;

import com.imadelfetouh.tweetservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.tweetservice.model.dto.NewFollowingDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;

public class AddFollowingExecuter implements QueryExecuter<Void> {

    private NewFollowingDTO newFollowingDTO;

    public AddFollowingExecuter(NewFollowingDTO newFollowingDTO) {
        this.newFollowingDTO = newFollowingDTO;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        Query query = session.createSQLQuery("INSERT INTO following (user_id, following_id) VALUES (:userId, :followingId)");
        query.setParameter("userId", newFollowingDTO.getUserId());
        query.setParameter("followingId", newFollowingDTO.getFollowingId());

        query.executeUpdate();

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
