package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.ProductCategoryVO;

import java.util.List;

public interface ProductCategoryService {

    /*
     * 商品分类
     * */
    ProductCategoryVO getCategoryVO(Integer categoryId);

    /*
     * 商品分类列表
     * */
    List<ProductCategoryVO> getCategoryVOList(String key, String lockKey, Integer homeStatus, Integer naviStatus, Integer limit);

    /*
    * 删除商品分类列表缓存
    * */
    void removeCacheCategoryVOList(String key);

}
