package com.imadelfetouh.tweetservice.dal.db;

import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.queryexecuter.LikeTweetExecuter;
import com.imadelfetouh.tweetservice.dalinterface.LikeDal;
import com.imadelfetouh.tweetservice.model.dto.LikeDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import com.imadelfetouh.tweetservice.rabbit.RabbitConfiguration;
import com.imadelfetouh.tweetservice.rabbit.RabbitProducer;
import com.imadelfetouh.tweetservice.rabbit.producer.AddLikeProducer;
import org.springframework.stereotype.Repository;

@Repository
public class LikeDalDB implements LikeDal {

    @Override
    public ResponseModel<Void> likeTweet(String userId, String tweetId) {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        ResponseModel<Void> responseModel = executer.execute(new LikeTweetExecuter(userId, tweetId));

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            LikeDTO likeDTO = new LikeDTO(userId, tweetId);
            RabbitProducer rabbitProducer = new RabbitProducer();
            rabbitProducer.produce(new AddLikeProducer(likeDTO));
        }

        return responseModel;
    }
}
