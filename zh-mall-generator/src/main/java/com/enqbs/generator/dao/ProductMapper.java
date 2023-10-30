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

    List<Product> selectListByParam(@Param("productCategoryId") Integer productCategoryId,
                                    @Param("saleableStatus") Integer saleableStatus,
                                    @Param("newStatus") Integer newStatus,
                                    @Param("recommendStatus") Integer recommendStatus,
                                    @Param("deleteStatus") Integer deleteStatus,
                                    @Param("pageNum") Integer pageNum,
                                    @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("productCategoryId") Integer productCategoryId,
                      @Param("saleableStatus") Integer saleableStatus,
                      @Param("newStatus") Integer newStatus,
                      @Param("recommendStatus") Integer recommendStatus,
                      @Param("deleteStatus") Integer deleteStatus);

}
