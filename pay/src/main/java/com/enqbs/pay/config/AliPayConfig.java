package com.enqbs.pay.config;

import com.alipay.v3.ApiClient;
import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.util.GenericExecuteApi;
import com.alipay.v3.util.model.AlipayConfig;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void initialAliPayClient() {
        ApiClient client = com.alipay.v3.Configuration.getDefaultApiClient();
        AlipayConfig config = new AlipayConfig();
        config.setServerUrl(gateway);
        config.setAppId(appId);
        config.setPrivateKey(appPrivateKey);
        config.setAlipayPublicKey(alipayPublicKey);

        try {
            client.setAlipayConfig(config);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public GenericExecuteApi payApi() {
        return new GenericExecuteApi();
    }

    @Bean
    public AlipayTradeApi tradeApi() {
        return new AlipayTradeApi();
    }

}
