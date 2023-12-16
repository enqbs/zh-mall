package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SpuSpec;

public interface SpuSpecMapper {

    int deleteByPrimaryKey(Integer spuId);

    int insert(SpuSpec record);

    int insertSelective(SpuSpec record);

    SpuSpec selectByPrimaryKey(Integer spuId);

    int updateByPrimaryKeySelective(SpuSpec record);

    int updateByPrimaryKey(SpuSpec record);

}
