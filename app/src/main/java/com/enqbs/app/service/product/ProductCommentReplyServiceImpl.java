package com.enqbs.app.service.product;

import com.enqbs.app.convert.ProductConvert;
import com.enqbs.app.form.ProductCommentReplyForm;
import com.enqbs.app.pojo.vo.ProductCommentReplyVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.service.user.UserService;
import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.ProductCommentReplyMapper;
import com.enqbs.generator.pojo.ProductCommentReply;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCommentReplyServiceImpl implements ProductCommentReplyService {

    @Resource
    private ProductCommentReplyMapper productCommentReplyMapper;

    @Resource
    private UserService userService;

    @Resource
    private ProductConvert productConvert;

    @Override
    public PageUtil<ProductCommentReplyVO> commentReplyVOListPage(Integer commentId, SortEnum sort, Integer pageNum, Integer pageSize) {
        Long total = productCommentReplyMapper.countByCommentId(commentId);
        List<ProductCommentReply> commentReplyList = productCommentReplyMapper.selectListByParam(commentId, sort.getSortType(), pageNum, pageSize);
        PageUtil<ProductCommentReplyVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(commentReplyList.stream().map(c -> productConvert.commentReply2CommentReplyVO(c)).toList());
        return pageUtil;
    }

    @Override
    public List<ProductCommentReplyVO> getCommentReplyVOList(Integer commentId) {
        List<ProductCommentReply> commentReplyList = productCommentReplyMapper.selectListByCommentId(commentId);
        return commentReplyList.stream().map(c -> productConvert.commentReply2CommentReplyVO(c)).toList();
    }

    @Override
    public int insert(ProductCommentReplyForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        ProductCommentReply commentReply = productConvert.form2CommentReply(form);
        commentReply.setUserId(userInfoVO.getUserId());
        commentReply.setNickName(userInfoVO.getNickName());
        commentReply.setPhoto(userInfoVO.getPhoto());
        return productCommentReplyMapper.insertSelective(commentReply);
    }

    @Override
    public int delete(Integer replyId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        int exist = productCommentReplyMapper.existByIdAndUserId(replyId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(exist)) {
            throw new ServiceException("评论不存在");
        }

        ProductCommentReply commentReply = new ProductCommentReply();
        commentReply.setId(replyId);
        commentReply.setDeleteStatus(Constants.IS_DELETE);
        return productCommentReplyMapper.updateByPrimaryKeySelective(commentReply);
    }

}
