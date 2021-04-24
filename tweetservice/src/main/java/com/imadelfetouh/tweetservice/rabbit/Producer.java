package com.imadelfetouh.tweetservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Producer {

    void produce(Channel channel);
}
