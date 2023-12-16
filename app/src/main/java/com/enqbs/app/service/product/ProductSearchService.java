package com.enqbs.app.service.product;

import com.enqbs.common.util.PageUtil;
import com.enqbs.search.pojo.ESProduct;

public interface ProductSearchService {

    /*
    * 商品搜索
    * */
    PageUtil<ESProduct> search(String searchText, Integer pageNum, Integer pageSize);

    /*
    * 修改搜索商品信息
    * */
    void update(Integer spuId);

    /*
    * 删除搜索商品信息
    * */
    void delete(Integer spuId);

}
