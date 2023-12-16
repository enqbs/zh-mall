package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductCategoryAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryAttributeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductCategoryAttribute record);

    int insertSelective(ProductCategoryAttribute record);

    ProductCategoryAttribute selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductCategoryAttribute record);

    int updateByPrimaryKey(ProductCategoryAttribute record);

    List<ProductCategoryAttribute> selectListByParam(@Param("categoryId") Integer categoryId,
                                                     @Param("deleteStatus") Integer deleteStatus,
                                                     @Param("pageNum") Integer pageNum,
                                                     @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("categoryId") Integer categoryId, @Param("deleteStatus") Integer deleteStatus);

}
