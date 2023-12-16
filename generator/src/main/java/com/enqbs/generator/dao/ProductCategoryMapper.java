package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> selectListByParam(@Param("parentId") Integer parentId,
                                            @Param("homeStatus") Integer homeStatus,
                                            @Param("naviStatus") Integer naviStatus,
                                            @Param("deleteStatus") Integer deleteStatus,
                                            @Param("pageNum") Integer pageNum,
                                            @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("parentId") Integer parentId,
                      @Param("homeStatus") Integer homeStatus,
                      @Param("naviStatus") Integer naviStatus,
                      @Param("deleteStatus") Integer deleteStatus);

    List<Integer> upperLimitByHomeOrNavi(@Param("homeStatus") Integer homeStatus, @Param("naviStatus") Integer naviStatus);

}
