package com.enqbs.app.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RabbitMQMessageListener {

    @RabbitListener(queues = "order.close.queue")
    public void orderCloseQueueListener(String body, Message message, Channel channel) throws IOException {
        log.info("body:{}", body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
