package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;

public interface ProductCategoryAttributeRelationMapper {

    int deleteByPrimaryKey(@Param("productCategoryId") Integer productCategoryId, @Param("productCategoryAttributeId") Integer productCategoryAttributeId);

    int insert(ProductCategoryAttributeRelation record);

    int insertSelective(ProductCategoryAttributeRelation record);

}
