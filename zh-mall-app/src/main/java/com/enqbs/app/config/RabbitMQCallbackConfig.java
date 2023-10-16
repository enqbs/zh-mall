package com.enqbs.app.config;

import com.enqbs.app.service.RabbitMQService;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.QueueEnum;
import com.enqbs.generator.pojo.MessageQueueLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/*
* RabbitMQ 消息发布确认、消息回退配置
* */
@Slf4j
@Configuration
public class RabbitMQCallbackConfig {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RabbitMQService rabbitMQService;

    @PostConstruct
    public void initRabbitTemplate() {
        confirmCallback();
        returnsCallback();
    }

    /*
    * 发布确认
    * */
    private void confirmCallback() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ObjectUtils.isNotEmpty(correlationData)) {
                MessageQueueLog messageQueueLog = rabbitMQService.getMessageQueueLog(Long.valueOf(correlationData.getId()));

                if (ObjectUtils.isNotEmpty(messageQueueLog)) {
                    if (ack) {
                        messageQueueLog.setStatus(Constants.MESSAGE_SEND_SUCCESS);
                    } else {
                        /* TODO 发送失败消息处理 */
                        messageQueueLog.setStatus(Constants.MESSAGE_SEND_ERROR);
                    }
                    rabbitMQService.updateMessageQueueLog(messageQueueLog);
                    log.info("ConfirmCallback:{}", messageQueueLog);
                }
            }
        });
    }

    /*
    * 消息回退
    * */
    private void returnsCallback() {
        rabbitTemplate.setReturnsCallback(returned -> {
            /* TODO 回退消息处理 */
            if (!QueueEnum.ORDER_CLOSE_QUEUE.getExchange().equals(returned.getExchange())) {
                log.info("Exchange:'{}',RoutingKey:'{}',ReplyText:'{}'",
                        returned.getExchange(), returned.getRoutingKey(), returned.getReplyText());
            }
        });
    }

}
