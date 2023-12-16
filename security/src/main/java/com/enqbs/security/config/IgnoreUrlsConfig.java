package com.enqbs.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    private String[] anonymous;     // 只允许未登录访问的 url

    private String[] permit;        // 允许匿名访问的 url

}
