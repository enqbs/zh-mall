package com.enqbs.common.enums;

public enum QueueEnum {

    ORDER_CLOSE_QUEUE("order.expired", "order.expired.close", "order.close.queue"),

    PAY_SUCCESS_QUEUE("pay.notify", "pay.notify.success", "pay.success.queue"),

    CANAL_SYNC_QUEUE("canal.sync", "canal.sync.data", "canal.data.queue");

    private final String exchange;

    private final String routingKey;

    private final String queue;

    public String getExchange() {
        return exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public String getQueue() {
        return queue;
    }

    QueueEnum(String exchange, String routingKey, String queue) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.queue = queue;
    }

}
