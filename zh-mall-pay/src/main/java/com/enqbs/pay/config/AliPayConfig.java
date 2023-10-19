package com.enqbs.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {

    private String gateway;

    private String notifyUrl;

    private String returnUrl;

    private String appId;

    private String appPrivateKey;

    private String alipayPublicKey;

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    @PostConstruct
    public AlipayClient initAlipayClient() {
        /*
        * ("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","utf-8","alipay_public_key","RSA2");
        * */
        return new DefaultAlipayClient(gateway, appId, appPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
    }

}
