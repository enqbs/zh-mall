package com.enqbs.app.service;

import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.generator.pojo.SkuStock;

import java.util.List;
import java.util.Set;

public interface SkuStockService {

    /*
    * 批量获取 sku 库存信息
    * */
    List<SkuStock> getSkuStockList(Set<Integer> skuIdSet);

    /*
    * 锁定商品库存
    * */
    void lockSkuStock(List<SkuStockDTO> skuStockDTOList);

    /*
    * 删减商品库存
    * */
    void unLockSkuStock(Long orderNo);

    /*
    * 删减商品库存
    * */
    void deleteSkuStock(Long orderNo);

}
