package com.enqbs.app.service.product;

import com.enqbs.app.convert.ProductConvert;
import com.enqbs.app.form.ProductCommentForm;
import com.enqbs.app.pojo.vo.ProductCommentVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.service.user.UserService;
import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.ProductCommentMapper;
import com.enqbs.generator.pojo.ProductComment;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    @Resource
    private ProductCommentMapper productCommentMapper;

    @Resource
    private ProductCommentReplyService productCommentReplyService;

    @Resource
    private UserService userService;

    @Resource
    private ProductConvert productConvert;

    @Override
    public PageUtil<ProductCommentVO> commentVOPage(Integer spuId, SortEnum sort, Integer pageNum, Integer pageSize) {
        PageUtil<ProductCommentVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<ProductComment> commentList = productCommentMapper.selectListByParam(spuId, sort.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(commentList)) {
            return pageUtil;
        }

        Long total = productCommentMapper.countBySpuId(spuId);
        List<ProductCommentVO> commentVOList = commentList.stream().map(c -> {
                    ProductCommentVO commentVO = productConvert.comment2CommentVO(c);
                    commentVO.setPictures(StringUtils.isEmpty(c.getPictures()) ?
                            Collections.emptyList() : GsonUtil.json2ArrayList(c.getPictures(), String[].class)
                    );
                    return commentVO;
                }
        ).toList();
        pageUtil.setTotal(total);
        pageUtil.setList(commentVOList);
        return pageUtil;
    }

    @Override
    public ProductCommentVO getCommentVO(Integer commentId) {
        ProductComment comment = productCommentMapper.selectByPrimaryKey(commentId);

        if (ObjectUtils.isEmpty(comment) || Constants.IS_DELETE.equals(comment.getDeleteStatus())) {
            return new ProductCommentVO();
        }

        ProductCommentVO commentVO = productConvert.comment2CommentVO(comment);
        commentVO.setPictures(StringUtils.isEmpty(comment.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(comment.getPictures(), String[].class)
        );
        commentVO.setReplyList(productCommentReplyService.getCommentReplyVOList(commentId));
        return commentVO;
    }

    @Override
    public int insert(ProductCommentForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        ProductComment comment = productConvert.form2Comment(form);
        comment.setUserId(userInfoVO.getUserId());
        comment.setNickName(userInfoVO.getNickName());
        comment.setPhoto(userInfoVO.getPhoto());
        comment.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return productCommentMapper.insertSelective(comment);
    }

    @Override
    public int update(Integer commentId, ProductCommentForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Integer exist = productCommentMapper.existByIdAndUserId(commentId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(exist)) {
            throw new ServiceException("商品评价不存在");
        }

        ProductComment comment = productConvert.form2Comment(form);
        comment.setId(commentId);
        comment.setUserId(userInfoVO.getUserId());
        comment.setNickName(userInfoVO.getNickName());
        comment.setPhoto(userInfoVO.getPhoto());
        comment.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return productCommentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public int delete(Integer commentId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Integer exist = productCommentMapper.existByIdAndUserId(commentId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(exist)) {
            throw new ServiceException("商品评价不存在");
        }

        ProductComment comment = new ProductComment();
        comment.setId(commentId);
        comment.setDeleteStatus(Constants.IS_DELETE);
        return productCommentMapper.updateByPrimaryKeySelective(comment);
    }

}
