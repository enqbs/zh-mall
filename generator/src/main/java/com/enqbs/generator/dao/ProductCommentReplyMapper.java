package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.ProductCommentReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCommentReplyMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductCommentReply record);

    int insertSelective(ProductCommentReply record);

    ProductCommentReply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductCommentReply record);

    int updateByPrimaryKey(ProductCommentReply record);

    List<ProductCommentReply> selectListByCommentId(Integer commentId);

    List<ProductCommentReply> selectListByParam(@Param("commentId") Integer commentId,
                                                @Param("sort") String sort,
                                                @Param("pageNum") Integer pageNum,
                                                @Param("pageSize") Integer pageSize);

    Long countByCommentId(Integer commentId);

    Integer existByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

}
