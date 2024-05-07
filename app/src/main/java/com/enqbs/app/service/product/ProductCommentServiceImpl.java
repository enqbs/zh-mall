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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public PageUtil<ProductCommentVO> productCommentVOListPage(Integer spuId, SortEnum sort, Integer pageNum, Integer pageSize) {
        Long total = productCommentMapper.countBySpuId(spuId);
        List<ProductComment> productCommentList = productCommentMapper.selectListByParam(spuId, sort.getSortType(), pageNum, pageSize);
        List<ProductCommentVO> productCommentVOList = productCommentList.stream().map(p -> {
                    ProductCommentVO productCommentVO = productConvert.productComment2ProductCommentVO(p);
                    productCommentVO.setPictures(StringUtils.isEmpty(p.getPictures()) ?
                            Collections.emptyList() : GsonUtil.json2ArrayList(p.getPictures(), String[].class)
                    );
                    return productCommentVO;
                }
        ).collect(Collectors.toList());
        PageUtil<ProductCommentVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(productCommentVOList);
        return pageUtil;
    }

    @Override
    public ProductCommentVO getProductCommentVO(Integer commentId) {
        ProductComment productComment = productCommentMapper.selectByPrimaryKey(commentId);
        ProductCommentVO productCommentVO = ObjectUtils.isEmpty(productComment)
                || Constants.IS_DELETE.equals(productComment.getDeleteStatus()) ? null : productConvert.productComment2ProductCommentVO(productComment);

        if (ObjectUtils.isNotEmpty(productCommentVO)) {
            productCommentVO.setPictures(StringUtils.isEmpty(productComment.getPictures()) ?
                    Collections.emptyList() : GsonUtil.json2ArrayList(productComment.getPictures(), String[].class)
            );
            productCommentVO.setReplyList(productCommentReplyService.getProductCommentReplyVOList(commentId));
        }

        return productCommentVO;
    }

    @Override
    public int insert(ProductCommentForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        ProductComment productComment = productConvert.productCommentForm2ProductComment(form);
        productComment.setUserId(userInfoVO.getUserId());
        productComment.setNickName(userInfoVO.getNickName());
        productComment.setPhoto(userInfoVO.getPhoto());
        productComment.setPictures(CollectionUtils.isEmpty(form.getPictures()) ?
                null : GsonUtil.obj2Json(form.getPictures())
        );
        return productCommentMapper.insertSelective(productComment);
    }

    @Override
    public int update(Integer commentId, ProductCommentForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Integer exist = productCommentMapper.existByIdAndUserId(commentId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(exist)) {
            throw new ServiceException("商品评价不存在");
        }

        ProductComment productComment = productConvert.productCommentForm2ProductComment(form);
        productComment.setId(commentId);
        productComment.setUserId(userInfoVO.getUserId());
        productComment.setNickName(userInfoVO.getNickName());
        productComment.setPhoto(userInfoVO.getPhoto());
        productComment.setPictures(CollectionUtils.isEmpty(form.getPictures()) ?
                null : GsonUtil.obj2Json(form.getPictures())
        );
        return productCommentMapper.updateByPrimaryKeySelective(productComment);
    }

    @Override
    public int delete(Integer commentId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Integer exist = productCommentMapper.existByIdAndUserId(commentId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(exist)) {
            throw new ServiceException("商品评价不存在");
        }

        ProductComment productComment = new ProductComment();
        productComment.setId(commentId);
        productComment.setDeleteStatus(Constants.IS_DELETE);
        return productCommentMapper.updateByPrimaryKeySelective(productComment);
    }

}
