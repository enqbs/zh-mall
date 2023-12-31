package com.enqbs.search.service.impl;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.enqbs.common.util.PageUtil;
import com.enqbs.search.constant.ESConstants;
import com.enqbs.search.pojo.ESProduct;
import com.enqbs.search.pojo.SearchParam;
import com.enqbs.search.service.ESProductService;
import com.enqbs.search.service.ESService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ESProductServiceImpl implements ESProductService {

    @Resource
    private ESService esService;

    @Override
    public PageUtil<ESProduct> search(String searchText, Integer pageNum, Integer pageSize) {
        PageUtil<ESProduct> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);

        SearchResponse<ESProduct> response;

        try {
            response = esService.search(getSearchParam(searchText, pageNum, pageSize), ESProduct.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<ESProduct> productList = response.hits().hits().stream().map(Hit::source).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(productList)) {
            return pageUtil;
        }

        pageUtil.setTotal(ObjectUtils.isEmpty(response.hits().total()) ? 0L : response.hits().total().value());
        pageUtil.setList(productList);
        return pageUtil;
    }

    @Override
    public void update(ESProduct product) {
        if (ObjectUtils.isNotEmpty(getESProduct(product.getId()))) {
            try {
                esService.update(ESConstants.INDEX_PRODUCT, String.valueOf(product.getId()), product, ESProduct.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            save(product);
        }
    }

    @Override
    public void delete(String spuId) {
        try {
            esService.delete(ESConstants.INDEX_PRODUCT, spuId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SearchParam getSearchParam(String searchText, Integer pageNum, Integer pageSize) {
        SearchParam param = new SearchParam();
        param.setIndex(ESConstants.INDEX_PRODUCT);
        param.setSearchField(ESConstants.SEARCH_FIELD_PRODUCT);
        param.setSearchText(searchText);
        param.setSortField(ESConstants.SORT_FIELD_PRODUCT);
        param.setSortOrder(SortOrder.Desc);
        param.setPageNum(pageNum);
        param.setPageSize(pageSize);
        return param;
    }

    private ESProduct getESProduct(Integer spuId) {
        try {
            return esService.get(ESConstants.INDEX_PRODUCT, String.valueOf(spuId), ESProduct.class).source();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(ESProduct product) {
        try {
            esService.index(ESConstants.INDEX_PRODUCT, String.valueOf(product.getId()), product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
