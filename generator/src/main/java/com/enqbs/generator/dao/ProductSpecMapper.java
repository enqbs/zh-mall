package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductSpec;
import org.apache.ibatis.annotations.Param;

public interface ProductSpecMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductSpec record);

    int insertSelective(ProductSpec record);

    ProductSpec selectByPrimaryKey(Integer id);

    ProductSpec selectByProductIdOrDeleteStatus(@Param("productId") Integer productId,
                                                @Param("deleteStatus") Integer deleteStatus);

    int updateByPrimaryKeySelective(ProductSpec record);

    int updateByPrimaryKey(ProductSpec record);

}
