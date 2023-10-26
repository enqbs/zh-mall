package com.enqbs.app.listener;

import com.enqbs.app.service.OrderService;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.RedisUtil;
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

    @Resource
    private RedisUtil redisUtil;

    @RabbitListener(queues = "order.close.queue")
    public void listenerOrderCloseQueue(String body, Message message, Channel channel) throws IOException {
        Order messageQueueOrder = GsonUtil.json2Obj(body, Order.class);
        orderService.handleTimeoutOrder(messageQueueOrder);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = "pay.success.queue")
    public void listenerPaySuccessQueue(String body, Message message, Channel channel) throws IOException {
        PayInfo messageQueuePayInfo = GsonUtil.json2Obj(body, PayInfo.class);
        orderService.handlePaySuccessOrder(messageQueuePayInfo);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = "canal.data.queue")
    public void listenerCanalSyncQueue(String body, Message message, Channel channel) throws IOException {
        /*
        * body:{
        *         "data":[{"id":"1","parent_id":"0","name":"手机","icon":null,"sort":"0","navi_status":"0","delete_status":"0"}],
        *         "database":"zh_mall",
        *         "es":1697784400000,
        *         "id":2,
        *         "isDdl":false,
        *         "mysqlType":{"id":"int unsigned","parent_id":"int unsigned","name":"varchar(128)","icon":"varchar(256)","sort":"int unsigned","navi_status":"tinyint unsigned","delete_status":"tinyint unsigned"},
        *         "old":[{"name":"手机1"}],
        *         "pkNames":["id"],
        *         "sql":"",
        *         "sqlType":{"id":4,"parent_id":4,"name":12,"icon":12,"sort":4,"navi_status":-6,"delete_status":-6},
        *         "table":"tb_product_category",
        *         "ts":1697784400883,
        *         "type":"UPDATE"}
        * */
        redisUtil.deleteObject(Constants.PRODUCT_CATEGORY_LIST);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.info("redis key:'{}',缓存同步成功", Constants.PRODUCT_CATEGORY_LIST);
    }

}
