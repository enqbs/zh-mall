package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SkuStockLock;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SkuStockLockMapper {

    int deleteByPrimaryKey(@Param("orderNo") Long orderNo, @Param("skuId") Integer skuId);

    int deleteByOrderNo(Long orderNo);

    int insert(SkuStockLock record);

    int insertSelective(SkuStockLock record);

    SkuStockLock selectByPrimaryKey(@Param("orderNo") Long orderNo, @Param("skuId") Integer skuId);

    int updateByPrimaryKeySelective(SkuStockLock record);

    int updateByPrimaryKey(SkuStockLock record);

    List<SkuStockLock> selectListByOrderNo(Long orderNo);

    int batchInsertBySkuStockLockList(@Param("skuStockLockList") List<SkuStockLock> skuStockLockList);

    List<SkuStockLock> selectListByOrderNoSet(@Param("orderNoSet") Set<Long> orderNoSet);

}
