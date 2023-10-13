package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.Sku;

import java.util.List;

public interface SkuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);

    List<Sku> selectListByProductId(Integer productId);

}
