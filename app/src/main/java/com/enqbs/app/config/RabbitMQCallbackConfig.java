package com.enqbs.app.config;

import com.enqbs.app.service.mq.RabbitMQService;
import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.QueueEnum;
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
                                    rabbitMQService.update(Long.valueOf(correlationData.getId()), Constants.MESSAGE_SEND_SUCCESS);
                                    log.info("ConfirmCallback:'{}',MessageID:'{}'.", true, correlationData.getId());
                                } else {
                                    rabbitMQService.update(Long.valueOf(correlationData.getId()), Constants.MESSAGE_SEND_ERROR);
                                    log.warn("ConfirmCallback:'{}',MessageID:'{}'.", false, correlationData.getId());
                                }
                            } catch (Exception e) {
                                flag = true;
                            }
                        } while (flag);
                    }
                }
        );
    }

    /*
     * 消息回退
     * */
    private void returnsCallback() {
        rabbitTemplate.setReturnsCallback(returned -> {
                    if (!QueueEnum.ORDER_CLOSE_QUEUE.getExchange().equals(returned.getExchange())) {
                        log.warn("MessageId:'{}',Exchange:'{}',RoutingKey:'{}',ReplyText:'{}',消息回退.",
                                returned.getMessage().getMessageProperties().getMessageId(), returned.getExchange(),
                                returned.getRoutingKey(), returned.getReplyText());
                        int i = 0;
                        boolean flag;

                        do {
                            flag = false;

                            try {
                                rabbitMQService.send(Long.valueOf(returned.getMessage().getMessageProperties().getMessageId()));
                            } catch (Exception e) {
                                flag = true;

                                if (16 == i) {
                                    log.error("MessageId:'{}',Exchange:'{}',RoutingKey:'{}',ReplyText:'{}',消息异常.",
                                            returned.getMessage().getMessageProperties().getMessageId(), returned.getExchange(),
                                            returned.getRoutingKey(), returned.getReplyText());
                                    throw e;
                                }

                                i += 1;
                            }
                        } while (flag);
                    }
                }
        );
    }

}
