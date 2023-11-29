package com.enqbs.app.service.mq;

import com.enqbs.common.enums.QueueEnum;

public interface RabbitMQService {

    /*
     * 发送普通消息
     * */
    void send(QueueEnum queue, Object content);

    /*
     * 发送 delay 消息
     * */
    void send(QueueEnum queue, Object content, Integer delay);

    /*
     * 使用 messageId 发送消息
     * */
    void send(Long messageId);

    /*
     * 更新 MessageQueueLog 状态
     * */
    void update(Long messageId, Integer status);

}
