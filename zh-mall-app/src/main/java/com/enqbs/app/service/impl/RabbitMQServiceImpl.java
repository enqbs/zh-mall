package com.enqbs.app.service.impl;

import com.enqbs.app.service.RabbitMQService;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.IDUtil;
import com.enqbs.generator.dao.MessageQueueLogMapper;
import com.enqbs.generator.pojo.MessageQueueLog;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

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
            throw new ServiceException("消息持久化失败");
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
            throw new ServiceException("消息持久化失败");
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
    public MessageQueueLog getMessageQueueLog(Long messageId) {
        return messageQueueLogMapper.selectByPrimaryKey(messageId);
    }

    @Override
    public int updateMessageQueueLog(MessageQueueLog messageQueueLog) {
        return messageQueueLogMapper.updateByPrimaryKeySelective(messageQueueLog);
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
