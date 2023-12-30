package com.enqbs.search.listener;

import com.enqbs.common.util.GsonUtil;
import com.enqbs.search.constant.ESConstants;
import com.enqbs.search.pojo.ESProduct;
import com.enqbs.search.service.ESProductService;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ESMessageListener {

    @Resource
    private ESProductService esProductService;

    @RabbitListener(queues = "es.sync.products.queue")
    public void listenerESSyncProductsQueue(String content, Message message, Channel channel) throws IOException {
        syncSearchProduct(content);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    private void syncSearchProduct(String content) {
        JsonObject jsonObject = GsonUtil.json2Obj(content, JsonObject.class);
        /* 旧数据存在删除旧数据 */
        Thread.ofVirtual().name("esProducts-deleteOldData").start(() -> jsonObject.getAsJsonArray("oldIds")
                .forEach(e -> {
                            if (StringUtils.isNotEmpty(e.getAsString())) {
                                esProductService.delete(e.getAsString());
                            }
                        }
                )
        );

        jsonObject.getAsJsonArray("data").forEach(e ->
                esProductService.update(GsonUtil.json2Obj(e.getAsJsonObject().getAsString(), ESProduct.class))
        );
        log.info("ES index:'{}',同步成功.", ESConstants.INDEX_PRODUCT);
    }

}
