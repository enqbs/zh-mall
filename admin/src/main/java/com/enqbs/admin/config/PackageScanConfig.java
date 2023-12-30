package com.enqbs.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.enqbs")
@MapperScan(basePackages = "com.enqbs.generator.dao")
public class PackageScanConfig {
}
