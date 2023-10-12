package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductComment;

public interface ProductCommentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductComment record);

    int insertSelective(ProductComment record);

    ProductComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductComment record);

    int updateByPrimaryKey(ProductComment record);

}
