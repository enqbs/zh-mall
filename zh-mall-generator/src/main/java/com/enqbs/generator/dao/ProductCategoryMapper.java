package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductCategory;

import java.util.List;

public interface ProductCategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> selectList();

}
