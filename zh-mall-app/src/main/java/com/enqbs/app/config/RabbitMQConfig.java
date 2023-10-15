package com.enqbs.app.config;

import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/*
* RabbitMQ 交换机、路由、队列绑定配置
* */
@Configuration
public class RabbitMQConfig {

    @Bean
    public CustomExchange orderExpiredExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(QueueEnum.ORDER_CLOSE_QUEUE.getExchange(), Constants.EXCHANGE_TYPE_DELAYED, true, false, arguments);
    }

    @Bean
    public Queue orderCloseQueue() {
        return new Queue(QueueEnum.ORDER_CLOSE_QUEUE.getQueue());
    }

    @Bean
    public Binding orderCloseQueueBindingOrderExpiredExchange(@Qualifier("orderCloseQueue") Queue queue,
                                                              @Qualifier("orderExpiredExchange") CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueEnum.ORDER_CLOSE_QUEUE.getRoutingKey()).noargs();
    }

}
