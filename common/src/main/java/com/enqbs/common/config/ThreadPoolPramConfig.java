package com.enqbs.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
* 自定义线程池参数
* */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolPramConfig {

    private int corePoolSize;   // 核心线程数

    private int maxPoolSize;    // 最大线程数

    private int workQueueSize;  // 阻塞队列数量

    private int keepAliveSeconds;  // 空闲线程存活时间(秒)

}
