package com.enqbs.app.listener;

import com.enqbs.app.service.order.OrderService;
import com.enqbs.common.util.GsonUtil;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OrderMessageListener {

    @Resource
    private OrderService orderService;

    @Async("executor")
    @RabbitListener(queues = "order.close.queue")
    public void listenerOrderCloseQueue(String content, Message message, Channel channel) throws IOException {
        Long orderNo = GsonUtil.json2Obj(content, Long.class);
        int i = 0;
        boolean flag;

        do {
            flag = false;

            try {
                orderService.handleTimeoutOrder(orderNo);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                flag = true;

                if (16 == i) {
                    handleBasicAckFail(content, message, channel);
                    break;
                }

                i += 1;
            }
        } while (flag);
    }

    @Async("executor")
    @RabbitListener(queues = "pay.success.queue")
    public void listenerPaySuccessQueue(String content, Message message, Channel channel) throws IOException {
        Long orderNo = GsonUtil.json2Obj(content, Long.class);
        int i = 0;
        boolean flag;

        do {
            flag = false;

            try {
                orderService.handlePaySuccessOrder(orderNo);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                flag = true;

                if (16 == i) {
                    handleBasicAckFail(content, message, channel);
                    break;
                }

                i += 1;
            }
        } while (flag);
    }

    private void handleBasicAckFail(String content, Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.error("消息队列:'{}',签收失败,content:'{}',message:'{}'.", message.getMessageProperties().getConsumerQueue(), content, message);
    }

}
