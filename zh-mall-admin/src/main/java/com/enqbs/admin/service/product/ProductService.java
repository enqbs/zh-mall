package com.enqbs.admin.service.product;

import com.enqbs.admin.vo.ProductVO;
import com.enqbs.common.util.PageUtil;

public interface ProductService {

    /*
     * 商品列表
     * */
    PageUtil<ProductVO> getProductVOList(Integer categoryId, Integer saleableStatus, Integer newStatus,
                                         Integer recommendStatus, Integer deleteStatus, Integer pageNum, Integer pageSize);

    /*
    * 商品详情
    * */
    ProductVO getProductVO(Integer productId);

}
