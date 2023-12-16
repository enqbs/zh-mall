package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryAttributeRelationMapper {

    int deleteByPrimaryKey(@Param("productCategoryId") Integer productCategoryId, @Param("productCategoryAttributeId") Integer productCategoryAttributeId);

    int deleteByProductCategoryId(Integer productCategoryId);

    int insert(ProductCategoryAttributeRelation record);

    int insertSelective(ProductCategoryAttributeRelation record);

    int batchInsertByProductCategoryAttributeRelationList(@Param("relationList") List<ProductCategoryAttributeRelation> relationList);

    int batchDeleteByProductCategoryAttributeRelationList(@Param("relationList") List<ProductCategoryAttributeRelation> relationList);

}
