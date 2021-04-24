package com.imadelfetouh.tweetservice.rabbit.delivercallback;

import com.google.gson.Gson;
import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.queryexecuter.AddFollowingExecuter;
import com.imadelfetouh.tweetservice.model.dto.NewFollowingDTO;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class AddFollowingDeliverCallback implements DeliverCallback {

    private final static Logger logger = Logger.getLogger(AddFollowingDeliverCallback.class.getName());

    private Gson gson;

    public AddFollowingDeliverCallback() {
        gson = new Gson();
    }

    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
        NewFollowingDTO newFollowingDTO = gson.fromJson(json, NewFollowingDTO.class);

        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        executer.execute(new AddFollowingExecuter(newFollowingDTO));
    }
}
