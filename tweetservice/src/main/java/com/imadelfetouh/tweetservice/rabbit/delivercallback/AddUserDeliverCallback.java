package com.imadelfetouh.tweetservice.rabbit.delivercallback;

import com.google.gson.Gson;
import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.queryexecuter.AddUserExecuter;
import com.imadelfetouh.tweetservice.model.dto.NewUserDTO;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddUserDeliverCallback implements DeliverCallback {

    private final static Logger logger = Logger.getLogger(AddUserDeliverCallback.class.getName());

    private Gson gson;

    public AddUserDeliverCallback() {
        gson = new Gson();
    }

    @Override
    public void handle(String s, Delivery delivery) {
        try {
            String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
            NewUserDTO newUserDTO = gson.fromJson(json, NewUserDTO.class);

            Executer<Void> executer = new Executer<>(SessionType.WRITE);
            executer.execute(new AddUserExecuter(newUserDTO));
        }
        catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
