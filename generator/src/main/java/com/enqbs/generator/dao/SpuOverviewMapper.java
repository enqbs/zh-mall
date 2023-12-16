package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SpuOverview;

public interface SpuOverviewMapper {

    int deleteByPrimaryKey(Integer spuId);

    int insert(SpuOverview record);

    int insertSelective(SpuOverview record);

    SpuOverview selectByPrimaryKey(Integer spuId);

    int updateByPrimaryKeySelective(SpuOverview record);

    int updateByPrimaryKey(SpuOverview record);

}
