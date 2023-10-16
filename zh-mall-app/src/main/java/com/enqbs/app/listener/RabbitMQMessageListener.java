package com.enqbs.app.listener;

import com.enqbs.app.service.OrderService;
import com.enqbs.app.service.SkuStockService;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.pojo.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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

    @Resource
    private SkuStockService skuStockService;

    @Resource
    private RedissonClient redissonClient;

    @RabbitListener(queues = "order.close.queue")
    public void listenerOrderCloseQueue(String body, Message message, Channel channel) throws IOException {
        log.info("body:{}", body);
        Order messageQueueOrder = GsonUtil.json2Obj(body, Order.class);
        orderService.handleTimeoutOrder(messageQueueOrder);
        RLock lock = redissonClient.getLock(Constants.SKU_STOCK_LOCK);
        lock.lock();

        try {
            skuStockService.unLockSkuStock(messageQueueOrder.getOrderNo());
        } finally {
            lock.unlock();
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
