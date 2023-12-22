package com.enqbs.app.enums;

import lombok.Getter;

@Getter
public enum QueueEnum {

    ORDER_CLOSE_QUEUE("order.expired", "close", "order.close.queue"),

    PAY_SUCCESS_QUEUE("pay.notify", "success", "pay.success.queue"),

    CANAL_SYNC_QUEUE("canal.data", "sync", "canal.sync.queue"),

    CANAL_SYNC_SPU_QUEUE("canal.data", "zh_mall_tb_spu", "canal.sync.spu.queue"),

    ES_SYNC_PRODUCTS_QUEUE("es.data", "sync.products", "es.sync.products.queue");

    private final String exchange;

    private final String routingKey;

    private final String queue;

    QueueEnum(String exchange, String routingKey, String queue) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.queue = queue;
    }

}
