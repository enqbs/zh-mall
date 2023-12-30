package com.enqbs.app.service.product;

import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.enums.OrderStatusEnum;
import com.enqbs.generator.pojo.SkuStock;

import java.util.List;
import java.util.Set;

public interface SkuStockService {

    /*
     * 批量获取 sku 库存信息
     * */
    List<SkuStock> getStockList(Set<Integer> skuIdSet);

    /*
     * 锁定商品库存
     * */
    void lockStock(Long orderNo, List<SkuStockDTO> stockDTOList);

    /*
     * 解锁商品库存
     * */
    void unLockStock(Long orderNo, OrderStatusEnum orderStatusEnum);

}
