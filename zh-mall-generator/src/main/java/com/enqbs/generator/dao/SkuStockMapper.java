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

    int updateByPrimaryKeySelective(SkuStock record);

    int updateByPrimaryKey(SkuStock record);

    List<SkuStock> selectListBySkuIdSet(@Param("skuIdSet") Set<Integer> skuIdSet);

    int batchUpdateBySkuStockList(@Param("stockList") List<SkuStock> stockList);

}
