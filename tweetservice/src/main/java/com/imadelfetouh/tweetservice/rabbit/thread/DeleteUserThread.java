package com.imadelfetouh.tweetservice.rabbit.thread;

import com.imadelfetouh.tweetservice.rabbit.RabbitNonStopConsumer;
import com.imadelfetouh.tweetservice.rabbit.consumer.DefaultConsumer;
import com.imadelfetouh.tweetservice.rabbit.delivercallback.DeleteUserDeliverCallback;
import com.rabbitmq.client.DeliverCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteUserThread implements Runnable {

    private final static Logger logger = Logger.getLogger(DeleteUserThread.class.getName());

    private final String queue_name;
    private final String exchange_name;
    private final DeliverCallback deliverCallback;

    public DeleteUserThread() {
        queue_name = "tweetservice_deleteuserconsumer";
        exchange_name = "deleteuserexchange";
        deliverCallback = new DeleteUserDeliverCallback();
    }

    @Override
    public void run() {
        while(true) {
            try {
                RabbitNonStopConsumer rabbitNonStopConsumer = new RabbitNonStopConsumer();
                DefaultConsumer defaultConsumer = new DefaultConsumer(queue_name, exchange_name, deliverCallback);

                rabbitNonStopConsumer.consume(defaultConsumer);
            } catch (Exception e) {
                logger.log(Level.ALL, e.getMessage());
            }
        }
    }
}
