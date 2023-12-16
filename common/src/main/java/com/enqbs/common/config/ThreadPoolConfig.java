package com.enqbs.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/*
* 自定义线程池配置
* */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(ThreadPoolPramConfig pram) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(pram.getCorePoolSize());
        executor.setMaxPoolSize(pram.getMaxPoolSize());
        executor.setQueueCapacity(pram.getWorkQueueSize());
        executor.setKeepAliveSeconds(pram.getKeepAliveSeconds());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
