package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SpuSlide;

public interface SpuSlideMapper {

    int deleteByPrimaryKey(Integer spuId);

    int insert(SpuSlide record);

    int insertSelective(SpuSlide record);

    SpuSlide selectByPrimaryKey(Integer spuId);

    int updateByPrimaryKeySelective(SpuSlide record);

    int updateByPrimaryKey(SpuSlide record);

}
