package com.enqbs.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
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
    public <T> SearchResponse<T> search(SearchParam param, Class<T> clazz) throws IOException {
        return esClient.search(s -> s
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
    }

    @Override
    public <T> GetResponse<T> get(String index, String id, Class<T> clazz) throws IOException {
        return esClient.get(g -> g
                .index(index)
                .id(id), clazz
        );
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
