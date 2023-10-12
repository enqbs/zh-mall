package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductSpec;

public interface ProductSpecMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductSpec record);

    int insertSelective(ProductSpec record);

    ProductSpec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductSpec record);

    int updateByPrimaryKey(ProductSpec record);

}
