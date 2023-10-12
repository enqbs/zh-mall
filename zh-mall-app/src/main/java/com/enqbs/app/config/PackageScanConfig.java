package com.enqbs.app.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.enqbs.generator.dao")
@ComponentScan(basePackages = "com.enqbs.*")
public class PackageScanConfig {
}
