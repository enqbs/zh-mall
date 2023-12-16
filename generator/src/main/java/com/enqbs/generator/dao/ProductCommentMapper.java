package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCommentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductComment record);

    int insertSelective(ProductComment record);

    ProductComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductComment record);

    int updateByPrimaryKey(ProductComment record);

    List<ProductComment> selectListByParam(@Param("spuId") Integer spuId,
                                           @Param("sort") String sort,
                                           @Param("pageNum") Integer pageNum,
                                           @Param("pageSize") Integer pageSize);

    Long countBySpuId(Integer spuId);

    Integer existByIdAndUserId(Integer id, Integer userId);

}
