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

import java.util.ArrayList;
import java.util.List;

@Configurable
public class ESConfig {

    @Resource
    private ESPramConfig ESPramConfig;

    @Bean
    public ElasticsearchClient esClient() {
        RestClient restClient = RestClient.builder(hosts()).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    private HttpHost[] hosts() {
        List<HttpHost> hostList = new ArrayList<>();

        for (String address : ESPramConfig.getAddress()) {
            String[] strings = address.split(":");
            hostList.add(new HttpHost(strings[0], Integer.parseInt(strings[1])));
        }

        return hostList.toArray(new HttpHost[] {});
    }

}
