package com.enqbs.pay.config;

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
@ConfigurationProperties(prefix = "ali.pay")
public class AliPayConfig {

    private String gateway;

    private String appId;

    private String appPrivateKey;

    private String alipayPublicKey;

    private String notifyUrl;

    private String returnUrl;

    @Bean(name = "aliPayClient")
    public AlipayClient aliPayClient() {
        /*
         * ("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","utf-8","alipay_public_key","RSA2");
         * */
        return new DefaultAlipayClient(gateway, appId, appPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
    }

}
