package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductOverview;

public interface ProductOverviewMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductOverview record);

    int insertSelective(ProductOverview record);

    ProductOverview selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductOverview record);

    int updateByPrimaryKey(ProductOverview record);

}
