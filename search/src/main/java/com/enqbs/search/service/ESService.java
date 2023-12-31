package com.enqbs.search.service;

import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.enqbs.search.pojo.SearchParam;

import java.io.IOException;

public interface ESService {

    /*
     * 简单搜索
     * */
    <T> SearchResponse<T> search(SearchParam param, Class<T> clazz) throws IOException;

    /*
     * Getting documents
     * */
    <T> GetResponse<T> get(String index, String id, Class<T> clazz) throws IOException;

    /*
     * Indexing documents
     * */
    void index(String index, String id, Object documents) throws IOException;

    /*
     * Updating documents
     * */
    <T> void update(String index, String id, T documents, Class<T> clazz) throws IOException;

    /*
     * Deleting documents
     * */
    void delete(String index, String id) throws IOException;

}
