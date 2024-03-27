package com.enqbs.search.service.impl;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.enqbs.common.util.PageUtil;
import com.enqbs.search.constant.ESConstants;
import com.enqbs.search.pojo.ESProduct;
import com.enqbs.search.pojo.SearchParam;
import com.enqbs.search.service.ESProductService;
import com.enqbs.search.service.ESService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ESProductServiceImpl implements ESProductService {

    @Resource
    private ESService esService;

    @Override
    public PageUtil<ESProduct> search(String searchText, Integer pageNum, Integer pageSize) {
        HitsMetadata<ESProduct> hitsMetadata;

        try {
            hitsMetadata = esService.search(getSearchParam(searchText, pageNum, pageSize), ESProduct.class).hits();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<ESProduct> productList = hitsMetadata.hits().stream().map(Hit::source).toList();
        PageUtil<ESProduct> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(ObjectUtils.isEmpty(hitsMetadata.total()) ? 0L : hitsMetadata.total().value());
        pageUtil.setList(productList);
        return pageUtil;
    }

    @Override
    public void update(ESProduct product) {
        try {
            ESProduct esProduct = esService.get(ESConstants.INDEX_PRODUCT, String.valueOf(product.getId()), ESProduct.class).source();

            if (ObjectUtils.isNotEmpty(esProduct)) {
                esService.update(ESConstants.INDEX_PRODUCT, String.valueOf(product.getId()), product, ESProduct.class);
            } else {
                esService.index(ESConstants.INDEX_PRODUCT, String.valueOf(product.getId()), product);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

}
