package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.ProductCategoryVO;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 商品分类、商品列表
     *
     * @param categoryId 商品类别 ID
     * @return 商品分类、商品列表信息
     */
    ProductCategoryVO getCategoryVO(Integer categoryId);

    /**
     * 商品分类列表
     *
     * @param key 缓存 key
     * @param lockKey 分布式锁 key
     * @param homeStatus 是否为首页分类列表
     * @param naviStatus 是否为导航分类列表
     * @param limit 限制分类数
     * @return 商品分类列表信息
     */
    List<ProductCategoryVO> getCategoryVOList(String key, String lockKey, Integer homeStatus, Integer naviStatus, Integer limit);

    /**
     * 删除商品分类列表缓存
     *
     * @param key 缓存 key
     */
    void removeCacheCategoryVOList(String key);

}
