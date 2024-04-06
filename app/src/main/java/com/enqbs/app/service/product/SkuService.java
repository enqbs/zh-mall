package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.SkuVO;

import java.util.List;
import java.util.Set;

public interface SkuService {

    /**
     * 商品 ID 获取商品规格列表
     *
     * @param spuId 商品 ID
     * @return 商品规格列表
     */
    List<SkuVO> getSkuVOList(Integer spuId);

    /**
     * 商品规格 ID 集合、商品 ID 集合获取商品规格列表
     * @param skuIdSet 商品规格 ID 集合
     * @param spuIdSet 商品 ID 集合
     * @return 商品规格列表
     */
    List<SkuVO> getSkuVOList(Set<Integer> skuIdSet, Set<Integer> spuIdSet);

}
