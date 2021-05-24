package com.imadelfetouh.tweetservice.dal.db;

import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.queryexecuter.AddTweetExecuter;
import com.imadelfetouh.tweetservice.dal.queryexecuter.GetTweetExecuter;
import com.imadelfetouh.tweetservice.dal.queryexecuter.TweetType;
import com.imadelfetouh.tweetservice.dalinterface.TweetDal;
import com.imadelfetouh.tweetservice.model.datetime.DateTime;
import com.imadelfetouh.tweetservice.model.dto.NewTweetDTO;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import com.imadelfetouh.tweetservice.rabbit.RabbitConfiguration;
import com.imadelfetouh.tweetservice.rabbit.RabbitProducer;
import com.imadelfetouh.tweetservice.rabbit.producer.AddTweetProducer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TweetDalDB implements TweetDal {

    @Override
    public ResponseModel<List<TweetDTO>> getTweets(String userId) {
        Executer<List<TweetDTO>> executer = new Executer<>(SessionType.READ);
        return executer.execute(new GetTweetExecuter(userId, null, TweetType.STANDARD));
    }

    @Override
    public ResponseModel<Void> addTweet(NewTweetDTO newTweetDTO) {
        Long currentDate = DateTime.getInstance().getCurrentDate();
        String currentTime = DateTime.getInstance().getCurrentTime();

        String tweetId = UUID.randomUUID().toString();

        newTweetDTO.setTweetId(tweetId);
        newTweetDTO.setDate(currentDate);
        newTweetDTO.setTime(currentTime);

        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        ResponseModel<Void> responseModel = executer.execute(new AddTweetExecuter(newTweetDTO));

        if(responseModel.getResponseType().equals(ResponseType.CORRECT) && RabbitConfiguration.getInstance().getConnection().isOpen()) {
            RabbitProducer rabbitProducer = new RabbitProducer();
            rabbitProducer.produce(new AddTweetProducer(newTweetDTO));
        }

        return responseModel;
    }
}
