package com.enqbs.search.service;

import com.enqbs.common.util.PageUtil;
import com.enqbs.search.pojo.ESProduct;

public interface ESProductService {

    /*
     * 商品搜索
     * */
    PageUtil<ESProduct> search(String searchText, Integer pageNum, Integer pageSize);

    /*
     * 修改搜索商品信息
     * */
    void update(ESProduct product);

    /*
     * 删除搜索商品信息
     * */
    void delete(String spuId);

}
