package com.enqbs.app.service.product;

import com.enqbs.generator.pojo.SkuStockLock;

import java.util.List;

public interface SkuStockLockService {

    /*
     * 库存锁定信息列表
     * */
    List<SkuStockLock> getSkuStockLockList(Long orderNo);

    /*
     * 批量插入库存锁定信息
     * */
    void batchInsert(Long orderNo, List<SkuStockLock> skuStockLockList);

    /*
     * 删除库存锁定信息
     * */
    void delete(Long orderNo);

}
