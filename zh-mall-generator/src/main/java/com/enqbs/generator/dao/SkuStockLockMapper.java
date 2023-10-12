package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SkuStockLock;
import org.apache.ibatis.annotations.Param;

public interface SkuStockLockMapper {

    int deleteByPrimaryKey(@Param("orderNo") Long orderNo, @Param("skuId") Integer skuId);

    int insert(SkuStockLock record);

    int insertSelective(SkuStockLock record);

    SkuStockLock selectByPrimaryKey(@Param("orderNo") Long orderNo, @Param("skuId") Integer skuId);

    int updateByPrimaryKeySelective(SkuStockLock record);

    int updateByPrimaryKey(SkuStockLock record);

}
