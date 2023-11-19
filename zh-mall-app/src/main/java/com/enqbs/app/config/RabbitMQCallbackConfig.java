package com.enqbs.app.config;

import com.enqbs.app.service.mq.RabbitMQService;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.QueueEnum;
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
                boolean flag;

                do {
                    flag = false;

                    try {
                        if (ack) {
                            rabbitMQService.updateMessageQueueLog(Long.valueOf(correlationData.getId()), Constants.MESSAGE_SEND_SUCCESS);
                            log.info("ConfirmCallback:'{}',MessageID:'{}'.", true, correlationData.getId());
                        } else {
                            rabbitMQService.updateMessageQueueLog(Long.valueOf(correlationData.getId()), Constants.MESSAGE_SEND_ERROR);
                            log.warn("ConfirmCallback:'{}',MessageID:'{}'.", false, correlationData.getId());
                        }
                    } catch (Exception e) {
                        flag = true;
                    }
                } while (flag);
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
                log.warn("Exchange:'{}',RoutingKey:'{}',ReplyText:'{}'.", returned.getExchange(), returned.getRoutingKey(), returned.getReplyText());
            }
        });
    }

}
