package com.enqbs.app.config;

import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
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
    public DirectExchange payNotifyExchange() {
        return new DirectExchange(QueueEnum.PAY_SUCCESS_QUEUE.getExchange());
    }

    @Bean
    public DirectExchange canalDataExchange() {
        return new DirectExchange(QueueEnum.CANAL_SYNC_QUEUE.getExchange());
    }

    @Bean
    public DirectExchange esDataExchange() {
        return new DirectExchange(QueueEnum.ES_SYNC_PRODUCTS_QUEUE.getExchange());
    }

    @Bean
    public Queue orderCloseQueue() {
        return new Queue(QueueEnum.ORDER_CLOSE_QUEUE.getQueue());
    }

    @Bean
    public Queue paySuccessQueue() {
        return new Queue(QueueEnum.PAY_SUCCESS_QUEUE.getQueue());
    }

    @Bean
    public Queue canalSyncQueue() {
        return new Queue(QueueEnum.CANAL_SYNC_QUEUE.getQueue());
    }

    @Bean
    public Queue canalSyncSpuQueue() {
        return new Queue(QueueEnum.CANAL_SYNC_SPU_QUEUE.getQueue());
    }

    @Bean
    public Queue canalSyncSkuQueue() {
        return new Queue(QueueEnum.CANAL_SYNC_SKU_QUEUE.getQueue());
    }

    @Bean
    public Queue esSyncProductsQueue() {
        return new Queue(QueueEnum.ES_SYNC_PRODUCTS_QUEUE.getQueue());
    }

    @Bean
    public Binding orderCloseQueueBindingOrderExpiredExchange(@Qualifier("orderCloseQueue") Queue queue,
                                                              @Qualifier("orderExpiredExchange") CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueEnum.ORDER_CLOSE_QUEUE.getRoutingKey()).noargs();
    }

    @Bean
    public Binding paySuccessQueueBindingPayNotifyExchange(@Qualifier("paySuccessQueue") Queue queue,
                                                           @Qualifier("payNotifyExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueEnum.PAY_SUCCESS_QUEUE.getRoutingKey());
    }

    @Bean
    public Binding canalSyncQueueBindingCanalDataExchange(@Qualifier("canalSyncQueue") Queue queue,
                                                          @Qualifier("canalDataExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueEnum.CANAL_SYNC_QUEUE.getRoutingKey());
    }

    @Bean
    public Binding canalSyncSpuQueueBindingCanalDataExchange(@Qualifier("canalSyncSpuQueue") Queue queue,
                                                             @Qualifier("canalDataExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueEnum.CANAL_SYNC_SPU_QUEUE.getRoutingKey());
    }

    @Bean
    public Binding canalSyncSkuQueueBindingCanalDataExchange(@Qualifier("canalSyncSkuQueue") Queue queue,
                                                             @Qualifier("canalDataExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueEnum.CANAL_SYNC_SKU_QUEUE.getRoutingKey());
    }

    @Bean
    public Binding esSyncProductsQueueBindingESDataExchange(@Qualifier("esSyncProductsQueue") Queue queue,
                                                            @Qualifier("esDataExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueEnum.ES_SYNC_PRODUCTS_QUEUE.getRoutingKey());
    }

}
