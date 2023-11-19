package com.enqbs.app.service.mq;

import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.IDUtil;
import com.enqbs.generator.dao.MessageQueueLogMapper;
import com.enqbs.generator.pojo.MessageQueueLog;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    @Resource
    private MessageQueueLogMapper messageQueueLogMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String exchange, String routingKey, Object message) {
        long messageId = IDUtil.getId();
        String content = GsonUtil.obj2Json(message);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(messageId));
        int row = saveMessageQueueLog(messageId, exchange, routingKey, content, null);

        if (row <= 0) {
            throw new ServiceException("MessageID:" + messageId + ",消息持久化失败");
        }

        rabbitTemplate.convertAndSend(exchange, routingKey, content, correlationData);
    }

    @Override
    public void send(String exchange, String routingKey, Object message, Integer delay) {
        long messageId = IDUtil.getId();
        String content = GsonUtil.obj2Json(message);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(messageId));
        int row = saveMessageQueueLog(messageId, exchange, routingKey, content, delay);

        if (row <= 0) {
            throw new ServiceException("MessageID:" + messageId + ",消息持久化失败");
        }

        rabbitTemplate.convertAndSend(exchange, routingKey, content, messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setDelay(delay);
            return messagePostProcessor;
        }, correlationData);
    }

    @Override
    public void send(MessageQueueLog messageQueueLog) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(messageQueueLog.getMessageId()));
        rabbitTemplate.convertAndSend(messageQueueLog.getExchange(), messageQueueLog.getRoutingKey(),
                messageQueueLog.getContent(), messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setDelay(messageQueueLog.getDelay());
                    return messagePostProcessor;
                }, correlationData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMessageQueueLog(Long messageId, Integer status) {
        MessageQueueLog messageQueueLog = messageQueueLogMapper.selectByPrimaryKey(messageId);

        if (ObjectUtils.isNotEmpty(messageQueueLog)) {
            messageQueueLog.setStatus(status);
        } else {
            messageQueueLog = messageQueueLogMapper.selectByPrimaryKey(messageId);
            messageQueueLog.setStatus(status);
        }

        int row = messageQueueLogMapper.updateByPrimaryKeySelective(messageQueueLog);

        if (row <= 0) {
            throw new ServiceException("MessageID:" + messageId + ",消息状态更新失败");
        }
    }

    private int saveMessageQueueLog(Long messageId, String exchange, String routingKey, String content, Integer delay) {
        MessageQueueLog messageQueueLog = new MessageQueueLog();
        messageQueueLog.setMessageId(messageId);
        messageQueueLog.setExchange(exchange);
        messageQueueLog.setRoutingKey(routingKey);
        messageQueueLog.setContent(content);
        messageQueueLog.setDelay(delay);
        return messageQueueLogMapper.insertSelective(messageQueueLog);
    }

}
