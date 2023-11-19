package com.enqbs.app.service.mq;

import com.enqbs.generator.pojo.MessageQueueLog;

public interface RabbitMQService {

    /*
     * 发送普通消息
     * */
    void send(String exchange, String routingKey, Object message);

    /*
     * 发送 delay 消息
     * */
    void send(String exchange, String routingKey, Object message, Integer delay);

    /*
     * 从 MessageQueueLog 中发送消息
     * */
    void send(MessageQueueLog messageQueueLog);

    /*
     * 更新 MessageQueueLog 状态
     * */
    void updateMessageQueueLog(Long messageId, Integer status);

}
