package com.enqbs.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.enqbs.search.pojo.SearchParam;
import com.enqbs.search.service.ESService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ESServiceImpl implements ESService {

    @Resource
    private ElasticsearchClient esClient;

    @Override
    public <T> HitsMetadata<T> search(SearchParam param, Class<T> clazz) throws IOException {
        SearchResponse<T> response = esClient.search(s -> s
                .index(param.getIndex())
                .query(q -> q
                        .match(m -> m
                                .field(param.getSearchField())
                                .query(param.getSearchText())
                        )
                )
                .sort(st -> st
                        .field(f -> f
                                .field(param.getSortField())
                                .order(param.getSortOrder())
                        )
                )
                .from((param.getPageNum() - 1) * param.getPageSize())
                .size(param.getPageSize()), clazz
        );
        return response.hits();
    }

    @Override
    public <T> T get(String index, String id, Class<T> clazz) throws IOException {
        GetResponse<T> response = esClient.get(g -> g
                .index(index)
                .id(id), clazz
        );
        return response.source();
    }

    @Override
    public void index(String index, String id, Object documents) throws IOException {
        esClient.index(i -> i
                .index(index)
                .id(id)
                .document(documents)
        );
    }

    @Override
    public <T> void update(String index, String id, T documents, Class<T> clazz) throws IOException {
        esClient.update(u -> u
                .index(index)
                .id(id)
                .upsert(documents), clazz
        );
    }

    @Override
    public void delete(String index, String id) throws IOException {
        esClient.delete(d -> d.index(index).id(id));
    }

}
