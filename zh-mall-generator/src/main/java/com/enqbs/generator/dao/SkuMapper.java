package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.Sku;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SkuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);

    List<Sku> selectListByProductId(Integer productId);

    List<Sku> selectListByProductIdSet(@Param("productIdSet") Set<Integer> productIdSet);

}
