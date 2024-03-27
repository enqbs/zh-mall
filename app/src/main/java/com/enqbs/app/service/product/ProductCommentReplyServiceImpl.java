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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCommentReplyServiceImpl implements ProductCommentReplyService {

    @Resource
    private ProductCommentReplyMapper productCommentReplyMapper;

    @Resource
    private UserService userService;

    @Resource
    private ProductConvert productConvert;

    @Override
    public PageUtil<ProductCommentReplyVO> productCommentReplyVOListPage(Integer commentId, SortEnum sort,
                                                                         Integer pageNum, Integer pageSize) {
        Long total = productCommentReplyMapper.countByCommentId(commentId);
        List<ProductCommentReply> productCommentReplyList = productCommentReplyMapper.selectListByParam(commentId, sort.getSortType(), pageNum, pageSize);
        PageUtil<ProductCommentReplyVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(productCommentReplyList.stream()
                .map(p -> productConvert.productCommentReply2ProductCommentReplyVO(p)).collect(Collectors.toList())
        );
        return pageUtil;
    }

    @Override
    public List<ProductCommentReplyVO> getProductCommentReplyVOList(Integer commentId) {
        List<ProductCommentReply> productCommentReplyList = productCommentReplyMapper.selectListByCommentId(commentId);
        return productCommentReplyList.stream().map(p -> productConvert.productCommentReply2ProductCommentReplyVO(p)).collect(Collectors.toList());
    }

    @Override
    public int insert(ProductCommentReplyForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        ProductCommentReply productCommentReply = productConvert.productCommentReplyForm2ProductCommentReply(form);
        productCommentReply.setUserId(userInfoVO.getUserId());
        productCommentReply.setNickName(userInfoVO.getNickName());
        productCommentReply.setPhoto(userInfoVO.getPhoto());
        return productCommentReplyMapper.insertSelective(productCommentReply);
    }

    @Override
    public int delete(Integer replyId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        int exist = productCommentReplyMapper.existByIdAndUserId(replyId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(exist)) {
            throw new ServiceException("评论不存在");
        }

        ProductCommentReply productCommentReply = new ProductCommentReply();
        productCommentReply.setId(replyId);
        productCommentReply.setDeleteStatus(Constants.IS_DELETE);
        return productCommentReplyMapper.updateByPrimaryKeySelective(productCommentReply);
    }

}
