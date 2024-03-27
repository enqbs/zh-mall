package com.enqbs.app.service.product;

import com.enqbs.app.form.ProductCommentReplyForm;
import com.enqbs.app.pojo.vo.ProductCommentReplyVO;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.util.PageUtil;

import java.util.List;

public interface ProductCommentReplyService {

    /*
     * 评论回复列表
     * */
    PageUtil<ProductCommentReplyVO> commentReplyVOListPage(Integer commentId, SortEnum sort, Integer pageNum, Integer pageSize);

    /*
     * 评论回复列表
     * */
    List<ProductCommentReplyVO> getCommentReplyVOList(Integer commentId);

    /*
     * 新增回复评论
     * */
    int insert(ProductCommentReplyForm form);

    /*
     * 删除回复评论
     * */
    int delete(Integer replyId);

}
