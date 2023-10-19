package com.enqbs.app.listener;

import com.enqbs.app.service.OrderService;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.pojo.Order;
import com.enqbs.generator.pojo.PayInfo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Component
public class RabbitMQMessageListener {

    @Resource
    private OrderService orderService;

    @RabbitListener(queues = "order.close.queue")
    public void listenerOrderCloseQueue(String body, Message message, Channel channel) throws IOException {
        log.info("body:{}", body);
        Order messageQueueOrder = GsonUtil.json2Obj(body, Order.class);
        orderService.handleTimeoutOrder(messageQueueOrder);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = "pay.success.queue")
    public void listenerPaySuccessQueue(String body, Message message, Channel channel) throws IOException {
        log.info("body:{}", body);
        PayInfo messageQueuePayInfo = GsonUtil.json2Obj(body, PayInfo.class);
        orderService.handlePaySuccessOrder(messageQueuePayInfo);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
