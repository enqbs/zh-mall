package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductCategoryAttribute;

public interface ProductCategoryAttributeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductCategoryAttribute record);

    int insertSelective(ProductCategoryAttribute record);

    ProductCategoryAttribute selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductCategoryAttribute record);

    int updateByPrimaryKey(ProductCategoryAttribute record);

}
