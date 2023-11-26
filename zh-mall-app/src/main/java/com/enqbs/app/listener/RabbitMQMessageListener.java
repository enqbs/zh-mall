package com.enqbs.app.listener;

import com.enqbs.app.service.order.OrderService;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.RedisUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Component
public class RabbitMQMessageListener {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private OrderService orderService;

    @Async("threadPoolTaskExecutor")
    @RabbitListener(queues = "order.close.queue")
    public void listenerOrderCloseQueue(String content, Message message, Channel channel) throws IOException {
        int i = 0;
        boolean flag;

        do {
            flag = false;

            try {
                Long orderNo = GsonUtil.json2Obj(content, Long.class);
                orderService.handleTimeoutOrder(orderNo);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                if (16 == i) {
                    handleBasicAckFail(content, message, channel);
                    break;
                }

                flag = true;
                i += 1;
            }
        } while (flag);
    }

    @Async("threadPoolTaskExecutor")
    @RabbitListener(queues = "pay.success.queue")
    public void listenerPaySuccessQueue(String content, Message message, Channel channel) throws IOException {
        int i = 0;
        boolean flag;

        do {
            flag = false;

            try {
                Long orderNo = GsonUtil.json2Obj(content, Long.class);
                orderService.handlePaySuccessOrder(orderNo);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                if (16 == i) {
                    handleBasicAckFail(content, message, channel);
                    break;
                }

                flag = true;
                i += 1;
            }
        } while (flag);
    }

    @Async("threadPoolTaskExecutor")
    @RabbitListener(queues = "canal.data.queue")
    public void listenerCanalSyncQueue(Message message, Channel channel) throws IOException {
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
        log.info("redis key:'{}',缓存同步成功.", Constants.PRODUCT_CATEGORY_LIST);
    }

    private void handleBasicAckFail(String content, Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.error("消息队列:'{}',确认失败,content:'{}',message:'{}'.",
                message.getMessageProperties().getConsumerQueue(), content, message);
    }

}
