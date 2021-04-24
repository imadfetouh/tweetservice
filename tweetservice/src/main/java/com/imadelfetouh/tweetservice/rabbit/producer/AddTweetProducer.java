package com.imadelfetouh.tweetservice.rabbit.producer;

import com.google.gson.Gson;
import com.imadelfetouh.tweetservice.model.dto.NewTweetDTO;
import com.imadelfetouh.tweetservice.rabbit.Producer;
import com.rabbitmq.client.Channel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddTweetProducer implements Producer {

    private static final Logger logger = Logger.getLogger(AddTweetProducer.class.getName());

    private final NewTweetDTO newTweetDTO;
    private final String exchange_name;
    private final Gson gson;

    public AddTweetProducer(NewTweetDTO newTweetDTO) {
        this.newTweetDTO = newTweetDTO;
        this.exchange_name = "addtweetexchange";
        gson = new Gson();
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(exchange_name, "direct", true);
            String json = gson.toJson(newTweetDTO);

            channel.basicPublish(exchange_name, "", null, json.getBytes());
        }
        catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
