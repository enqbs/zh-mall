package com.enqbs.app.listener;

import com.enqbs.app.service.product.ProductCategoryService;
import com.enqbs.app.service.product.SpuService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_NAVI;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_HOME;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_BANNER;

@Slf4j
@Component
public class CanalMessageListener {

    @Resource
    private ProductCategoryService productCategoryService;

    @Resource
    private SpuService spuService;

    @Resource
    private ThreadPoolTaskExecutor executor;

    @Async("threadPoolTaskExecutor")
    @RabbitListener(queues = "canal.sync.queue")
    public void listenerCanalSyncQueue(Message message, Channel channel) throws IOException {
        syncCacheProductCategoryList();
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @Async("threadPoolTaskExecutor")
    @RabbitListener(queues = "canal.sync.spu.queue")
    public void listenerCanalSyncSpuQueue(String content, Message message, Channel channel) throws IOException {
        executor.execute(() -> spuService.syncESProducts(content));
        syncCacheProductCategoryList();
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    private void syncCacheProductCategoryList() {
        executor.execute(() -> productCategoryService.removeCacheCategoryVOList(PRODUCT_CATEGORY_LIST));
        executor.execute(() -> productCategoryService.removeCacheCategoryVOList(PRODUCT_CATEGORY_LIST_NAVI));
        executor.execute(() -> productCategoryService.removeCacheCategoryVOList(PRODUCT_CATEGORY_LIST_HOME));
        executor.execute(() -> productCategoryService.removeCacheCategoryVOList(PRODUCT_CATEGORY_LIST_BANNER));
        log.info("redis key:'{}',缓存同步成功.", PRODUCT_CATEGORY_LIST);
    }

}
