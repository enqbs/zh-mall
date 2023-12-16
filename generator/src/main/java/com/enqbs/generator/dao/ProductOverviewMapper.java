package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductOverview;
import org.apache.ibatis.annotations.Param;

public interface ProductOverviewMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductOverview record);

    int insertSelective(ProductOverview record);

    ProductOverview selectByPrimaryKey(Integer id);

    ProductOverview selectByProductIdOrDeleteStatus(@Param("productId") Integer productId,
                                                    @Param("deleteStatus") Integer deleteStatus);

    int updateByPrimaryKeySelective(ProductOverview record);

    int updateByPrimaryKey(ProductOverview record);

}
