package com.enqbs.file.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 阿里云 OSS 配置
 * */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ali.oss")
public class AliOSSConfig {

    private String endpoint;

    private String bucketDomain;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucket;

    @Bean(name = "ossClient")
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

}
