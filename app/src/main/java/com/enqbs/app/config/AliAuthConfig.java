package com.enqbs.app.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ali.auth")
public class AliAuthConfig {

    private String gateway;

    private String appId;

    private String appPrivateKey;

    private String alipayPublicKey;

    @Bean
    public AlipayClient aliAuthClient() {
        return new DefaultAlipayClient(gateway, appId, appPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
    }

}
