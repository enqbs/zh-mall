package com.enqbs.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/*
* 自定义线程池配置
* */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolConfig {

    private int corePoolSize;   // 核心线程数

    private int maxPoolSize;    // 最大线程数

    private int workQueueSize;  // 阻塞队列数量

    private int keepAliveSeconds;  // 空闲线程存活时间(秒)

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(workQueueSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
