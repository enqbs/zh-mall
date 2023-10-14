package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectListByCategoryId(Integer productCategoryId);

    List<Product> selectListByProductIdSet(@Param("productIdSet") Set<Integer> productIdSet);

}
