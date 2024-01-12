package com.enqbs.app.service.product;

import com.enqbs.app.form.ProductCommentForm;
import com.enqbs.app.pojo.vo.ProductCommentVO;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.util.PageUtil;

public interface ProductCommentService {

    /*
     * 商品评价列表
     * */
    PageUtil<ProductCommentVO> productCommentVOListPage(Integer spuId, SortEnum sort, Integer pageNum, Integer pageSize);

    /*
    * 商品评价详情
    * */
    ProductCommentVO getProductCommentVO(Integer commentId);

    /*
     * 新增商品评价
     * */
    int insert(ProductCommentForm form);

    /*
     * 修改商品评价
     * */
    int update(Integer commentId, ProductCommentForm form);

    /*
     * 删除商品评价
     * */
    int delete(Integer commentId);

}
