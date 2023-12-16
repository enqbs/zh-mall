package com.enqbs.app.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackages = "com.enqbs")
@MapperScan(basePackages = "com.enqbs.generator.dao")
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
public class PackageScanConfig {
}
