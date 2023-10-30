package com.enqbs.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
* 自定义线程池参数
* */
@Configuration
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolPramConfig {


    private int corePoolSize;   // 核心线程数

    private int maxPoolSize;    // 最大线程数

    private int workQueueSize;  // 阻塞队列数量

    private int keepAliveSeconds;  // 空闲线程存活时间(秒)

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getWorkQueueSize() {
        return workQueueSize;
    }

    public void setWorkQueueSize(int workQueueSize) {
        this.workQueueSize = workQueueSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

}
