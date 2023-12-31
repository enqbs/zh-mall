package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SkuStockMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SkuStock record);

    int insertSelective(SkuStock record);

    SkuStock selectByPrimaryKey(Integer id);

    SkuStock selectBySkuId(Integer skuId);

    int updateByPrimaryKeySelective(SkuStock record);

    int updateByPrimaryKey(SkuStock record);

    List<SkuStock> selectListBySkuIdSet(@Param("skuIdSet") Set<Integer> skuIdSet);

    int batchUpdateByStockListLockStock(@Param("stockList") List<SkuStock> stockList);

    int batchUpdateByStockListUnLockStockRollback(@Param("stockList") List<SkuStock> stockList);

    int batchUpdateByStockListUnLockStockDelete(@Param("stockList") List<SkuStock> stockList);

}
