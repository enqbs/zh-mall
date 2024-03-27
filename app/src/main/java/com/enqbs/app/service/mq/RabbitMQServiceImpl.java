package com.enqbs.app.service.mq;

import com.enqbs.app.enums.QueueEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.IDUtil;
import com.enqbs.generator.dao.MessageQueueLogMapper;
import com.enqbs.generator.pojo.MessageQueueLog;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    @Resource
    private MessageQueueLogMapper messageQueueLogMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(QueueEnum queue, Object content) {
        long messageId = IDUtil.getId();
        String message = GsonUtil.obj2Json(content);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(messageId));
        insert(messageId, queue.getExchange(), queue.getRoutingKey(), queue.getQueue(), message, null);
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey(), message, correlationData);
    }

    @Override
    public void send(QueueEnum queue, Object content, Integer delay) {
        long messageId = IDUtil.getId();
        String message = GsonUtil.obj2Json(content);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(messageId));
        insert(messageId, queue.getExchange(), queue.getRoutingKey(), queue.getQueue(), message, delay);
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey(), message, m -> {
                    m.getMessageProperties().setDelayLong(delay.longValue());
                    return m;
                }, correlationData
        );
    }

    @Override
    public void send(Long messageId) {
        MessageQueueLog messageQueueLog = messageQueueLogMapper.selectByPrimaryKey(messageId);

        if (ObjectUtils.isEmpty(messageQueueLog)) {
            throw new ServiceException("MessageID:" + messageId + ",消息不存在");
        }

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(messageQueueLog.getMessageId()));
        rabbitTemplate.convertAndSend(messageQueueLog.getExchange(), messageQueueLog.getRoutingKey(),
                messageQueueLog.getContent(), m -> {
                    m.getMessageProperties().setDelayLong(messageQueueLog.getDelay().longValue());
                    return m;
                }, correlationData
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long messageId, Integer status) {
        MessageQueueLog messageQueueLog = new MessageQueueLog();
        messageQueueLog.setMessageId(messageId);
        messageQueueLog.setStatus(status);
        int row = messageQueueLogMapper.updateByPrimaryKeySelective(messageQueueLog);

        if (row <= 0) {
            throw new ServiceException("MessageID:" + messageId + ",消息状态更新失败");
        }
    }

    private void insert(Long messageId, String exchange, String routingKey, String queue, String content, Integer delay) {
        MessageQueueLog messageQueueLog = new MessageQueueLog();
        messageQueueLog.setMessageId(messageId);
        messageQueueLog.setExchange(exchange);
        messageQueueLog.setRoutingKey(routingKey);
        messageQueueLog.setQueue(queue);
        messageQueueLog.setContent(content);
        messageQueueLog.setDelay(delay);
        messageQueueLogMapper.insertSelective(messageQueueLog);
    }

}
