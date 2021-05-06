package com.imadelfetouh.tweetservice.rabbit.producer;

import com.google.gson.Gson;
import com.imadelfetouh.tweetservice.model.dto.LikeDTO;
import com.imadelfetouh.tweetservice.rabbit.Producer;
import com.rabbitmq.client.Channel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddLikeProducer implements Producer {

    private static final Logger logger = Logger.getLogger(AddLikeProducer.class.getName());

    private final LikeDTO likeDTO;
    private final String exchange_name;
    private final Gson gson;

    public AddLikeProducer(LikeDTO likeDTO) {
        this.likeDTO = likeDTO;
        this.exchange_name = "addlikeexchange";
        gson = new Gson();
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(exchange_name, "direct", true);
            String json = gson.toJson(likeDTO);

            channel.basicPublish(exchange_name, "", null, json.getBytes());
        }
        catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
