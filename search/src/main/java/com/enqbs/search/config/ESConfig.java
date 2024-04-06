package com.enqbs.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.annotation.Resource;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@Configurable
public class ESConfig {

    @Resource
    private ESPramConfig ESPramConfig;

    @Bean
    public ElasticsearchClient esClient() {
        RestClient restClient = RestClient.builder(getHosts()).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    private HttpHost[] getHosts() {
        List<HttpHost> hostList = Arrays.stream(ESPramConfig.getAddress()).map(a -> {
                    String[] strings = a.split(":");
                    return new HttpHost(strings[0], Integer.parseInt(strings[1]));
                }
        ).toList();
        return hostList.toArray(new HttpHost[] {});
    }

}
