package com.enqbs.app.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@MapperScan(basePackages = "com.enqbs.generator.dao")
@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan(basePackages = "com.enqbs")
public class PackageScanConfig {
}
