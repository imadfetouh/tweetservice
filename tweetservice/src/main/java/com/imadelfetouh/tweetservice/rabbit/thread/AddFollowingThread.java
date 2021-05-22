package com.imadelfetouh.tweetservice.rabbit.thread;

import com.imadelfetouh.tweetservice.rabbit.RabbitNonStopConsumer;
import com.imadelfetouh.tweetservice.rabbit.consumer.DefaultConsumer;
import com.imadelfetouh.tweetservice.rabbit.delivercallback.AddFollowingDeliverCallback;
import com.rabbitmq.client.DeliverCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddFollowingThread implements Runnable {

    private Logger logger = Logger.getLogger(AddFollowingThread.class.getName());

    private final String queue_name;
    private final String exchange_name;
    private final DeliverCallback deliverCallback;

    public AddFollowingThread() {
        queue_name = "tweetservice_addfollowingconsumer";
        exchange_name = "addfollowingexchange";
        deliverCallback = new AddFollowingDeliverCallback();
    }

    @Override
    public void run() {
        StartConsuming startConsuming = new StartConsuming(queue_name, exchange_name, deliverCallback);
        startConsuming.start();
    }
}
