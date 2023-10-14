package com.enqbs.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    private List<String> anonymous;

    private List<String> permit;

    public List<String> getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(List<String> anonymous) {
        this.anonymous = anonymous;
    }

    public List<String> getPermit() {
        return permit;
    }

    public void setPermit(List<String> permit) {
        this.permit = permit;
    }

}
